package com.integration.utils;

import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.AMQP.Queue.DeleteOk;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;
/**
* @Package: com.integration.utils
* @ClassName: RabbitUtil
* @Author: ztl
* @Date: 2021-08-10
* @Version: 1.0
* @description: rabbitMQ工具类
*/
@Component
public class RabbitUtil {

    /**
     * 动态生成队列
     * @param name
     * @throws IOException
     */
    public static DeclareOk addQueues(String name)  {
        DeclareOk isOk=null;
        ConnectionFactory connectionFactory=SpringContextUtil.getBean(ConnectionFactory.class);
        try {
             isOk =connectionFactory.
                    createConnection().
                    createChannel(false).
                    queueDeclare(name, true, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    /**
     * 动态删除队列
     * @param name
     * @return
     */
    public static  DeleteOk deleteQueues(String name)  {
        ConnectionFactory connectionFactory=SpringContextUtil.getBean(ConnectionFactory.class);
        DeleteOk isOk=null;
        try {
            isOk = connectionFactory.
                    createConnection().
                    createChannel(false).
                    queueDelete(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isOk;
    }

    /**
     * 查询是否存在队列
     * @param name
     * @return
     */
    public static  DeclareOk findQueuesByName(String name) throws IOException {
        ConnectionFactory connectionFactory=SpringContextUtil.getBean(ConnectionFactory.class);
        DeclareOk isOk =connectionFactory.
                    createConnection().
                    createChannel(false).queueDeclarePassive(name);

        return isOk;
    }


}


