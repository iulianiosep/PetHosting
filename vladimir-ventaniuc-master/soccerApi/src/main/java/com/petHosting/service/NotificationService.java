package com.petHosting.service;

import com.petHosting.entity.Notification;
import com.petHosting.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    public List<Notification> findAllByUserId(Long userId) {
        List<Notification> notifications = (List<Notification>) notificationRepository.findAllByUserId(userId);
        return notifications;
    }

    public void deleteAllByUserId(Long userId){
        notificationRepository.deleteAllByUserId(userId);
    }

    public void delete(Notification notification){
        this.notificationRepository.delete(notification);
    }

    public Optional<Notification> findById(Long id){
        return this.notificationRepository.findById(id);
    }

    public Notification add(Long userId, String message){
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notificationRepository.save(notification);
        List<Notification> notifications = (List<Notification>) notificationRepository.findAllByUserId(userId);
        return notifications.isEmpty() ? notification : notifications.get(0);
    }


}
