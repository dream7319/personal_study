package lierl.other.avoid.form.repeat.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;

/**
 * 使用redis实现分布式锁
 * @Author: lierl
 * @Description:
 * @Date: 2018/6/20 22:22
 */
public class RedisDistributeLock {
    private static final String LOCK_SUCCESS="OK";
    private static final String SET_IF_NO_EXIST="NX";
    private static final String SET_WITH_EXPIRE_TIME="PX";
    private static final Long RELEASE_SUCCESS=1L;

    private static void validParam(JedisPool jedisPool, String lockKey, String requestId, int expireTime) {
        if (null == jedisPool) {
            throw new IllegalArgumentException("jedisPool obj is null");
        }

        if (null == lockKey || "".equals(lockKey)) {
            throw new IllegalArgumentException("lock key  is blank");
        }

        if (null == requestId || "".equals(requestId)) {
            throw new IllegalArgumentException("requestId is blank");
        }

        if (expireTime < 0) {
            throw new IllegalArgumentException("expireTime is not allowed less zero");
        }
    }

    /**
     *
     * @param jedisPool
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @return
     */
    public static boolean tryLock(JedisPool jedisPool,String lockKey,String requestId,int expireTime){
       validParam(jedisPool,lockKey,requestId,expireTime);
       Jedis jedis = null;
       jedis = jedisPool.getResource();
        /**
         * final String key,
         * final String value,
         * final String nxxx, nxxx为模式，这里我们设置为NX，意思是说如果key不存在则插入该key对应的value并返回OK，
         *                      否者什么都不做返回null；
         * final String expx, 这里我们设置为PX，意思是设置key的过期时间为time 毫秒
         * final int time
         */
       String result = jedis.set(lockKey, requestId, SET_IF_NO_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
       if(LOCK_SUCCESS.equals(result)){
           jedis.close();
           return true;
       }

       return false;
    }

    /**
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     * @param expireTime
     */
    public static void lock(JedisPool jedisPool, String lockKey, String requestId, int expireTime) {

        validParam(jedisPool, lockKey, requestId, expireTime);

        while (true) {
            if (tryLock(jedisPool, lockKey, requestId, expireTime)) {
                return;
            }
        }
    }

    /**
     *
     * @param jedis
     * @param lockKey
     * @param requestId
     * @return
     */
    public static boolean unLock(JedisPool jedisPool, String lockKey, String requestId) {

        validParam(jedisPool, lockKey, requestId, 1);
        /**
         * Redis有一个叫做eval的函数，支持Lua脚本执行，并且能够保证脚本执行的原子性，也就是在执行脚本期间，
         * 其它执行redis命令的线程都会被阻塞。这里解锁时候使用下面脚本：
         *
         * 其中keys[1]为unLock方法传递的key，argv[1]为unLock方法传递的requestId;脚本redis.call('get', KEYS[1])的作用是
         * 获取key对应的value值，这里会返回通过Lock方法传递的requetId, 然后看当前传递的RequestId是否等于key对应的值，
         * 等于则说明当前要释放锁的线程就是获取锁的线程，则继续执行redis.call('del', KEYS[1])脚本，
         * 删除key对应的值。
         *
         */
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        Jedis jedis = null;
        try {

            jedis = jedisPool.getResource();
            Object result = jedis.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(requestId));

            if (RELEASE_SUCCESS.equals(result)) {
                return true;
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }

        return false;

    }
}
