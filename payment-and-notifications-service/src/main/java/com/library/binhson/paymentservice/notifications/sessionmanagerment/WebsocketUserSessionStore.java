package com.library.binhson.paymentservice.notifications.sessionmanagerment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class WebsocketUserSessionStore {
    private final Lock lock = new ReentrantLock();
    // đảm bảo rằng chỉ có một luồng có thể truy cập vào một phần của mã tại một thời điểm
    private final Map<String, String> store = new HashMap<>();

    public void add(String sessionId, String userId) {
        lock.lock();
        log.info("add : "+sessionId);
        try {
            store.put(sessionId, userId);
        } finally {
            lock.unlock();
        }
    }

    public void removeBySession(String sessionId) {
        lock.lock();
        //Khoa da luonn
        try {
            store.remove(sessionId);
        } finally {
            lock.unlock();
        }
    }

    public void removeByUser(String userId) {
        lock.lock();
        try {
            store.values().remove(userId);
        } finally {
            lock.unlock();
        }
    }

    public List<String> getSessionIds(List<String> userIds) {
        List<String> sessionIds=new ArrayList<>();
        for(Map.Entry<String, String> entry: store.entrySet()){
            if(userIds.contains(entry.getValue().trim()))
                sessionIds.add(entry.getKey());
        }
        return sessionIds;
    }

    public List<String> getSessionIdsAll() {
        List<String> sessionIds=new ArrayList<>();
        for(Map.Entry<String, String> entry: store.entrySet()){
            sessionIds.add(entry.getKey());
        }
        return sessionIds;
    }

    public Map<String, String> getDataMap() {
        return new HashMap<>(store);
    }
}
