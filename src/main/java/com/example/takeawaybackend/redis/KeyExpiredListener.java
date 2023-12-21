package com.example.takeawaybackend.redis;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.takeawaybackend.bean.DishOrder;
import com.example.takeawaybackend.dao.DishOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Component
@Service
@Controller
public class KeyExpiredListener extends KeyExpirationEventMessageListener {
    @Autowired
    private DishOrderDao dishOrderDao;
    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 使用该方法监听，当Redis的key失效的时候执行该方法
     * @param message message must not be {@literal null}.
     * @param pattern pattern matching the channel (if specified) - can be {@literal null}.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 过期的Key
        String expiraKey = message.toString();
        System.out.println("该Key已失效："+expiraKey);
        String result = expiraKey.substring(7);
        System.out.println(result);

        QueryWrapper<DishOrder> wrapper=new QueryWrapper<DishOrder>()
                .eq("id",result);
        DishOrder dishOrder=dishOrderDao.selectOne(wrapper);
        System.out.println(dishOrder.getId());
        if(dishOrder.getState().equals("提交订单")){
            dishOrder.setState("已取消");
            int update=dishOrderDao.update(dishOrder,wrapper);
            System.out.println("更新结果："+update);
        }
        System.out.println("成功");
        System.out.println(dishOrder);
    }

}