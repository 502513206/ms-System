package com.zxw.utils;

import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author zxw
 * @date 2020/7/23 14:52
 */
public class RedissonUtils {
    public static RedissonClient redissonClient;

    static {
        Config config = new Config();
        String redisUrl = String.format("redis://%s:%s", "localhost", "6379");
        config.useSingleServer().setAddress(redisUrl);
        redissonClient = Redisson.create(config);
    }

    /**
     *
     * @param redisKey redis hash name
     * @param key keyname
     * @param limitNum 限制访问次数
     *                 if request_times == 1 then redis.call('expire',KEYS[1], ARGV[1]) end;
     * @return 0:false(超过访问次数) 1:true
     */
    public static Integer IpLimit(String redisKey, String key, String limitNum) {
        Integer num = (Integer)redissonClient.getScript().eval(RScript.Mode.READ_WRITE, buildLua("eval local request_times = redis.call('hincrby',KEYS[1],ARGV[1],1);if request_times > tonumber(ARGV[2]) then return 0 end return 1;", "1", redisKey, key, limitNum), RScript.ReturnType.INTEGER);
        return num;
    }

    private static String buildLua(String statement, String keyNum, String key, String... params) {
        StringBuffer luaScript = new StringBuffer();
        luaScript.append(statement)
                .append(" " + keyNum)
                .append(" " + key);
        for (String param : params) {
            luaScript.append(" " + param);
        }
        return luaScript.toString();
    }
}
