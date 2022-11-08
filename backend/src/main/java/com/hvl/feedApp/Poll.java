package com.hvl.feedApp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hvl.feedApp.Enums.Status;
import com.hvl.feedApp.config.RabbitMQConfig;
import com.hvl.feedApp.controller.MessageSendController;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Poll {
    @Id
    @SequenceGenerator(name = "poll_sequence", sequenceName = "poll_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "poll_sequence")
    @Column(name="poll_id")
    private long pollID;
    // Relation
    @ManyToOne(targetEntity = Agent.class)
    private Agent owner;
    // Attributes
    private boolean isPrivate;
    private int pin;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private int yesCount;
    private int noCount;
    private String question;
    @Transient
    @Enumerated(EnumType.STRING)
    private Status status;

    private static RabbitTemplate rabbitTemplate;
    private static MessageSendController sendController;
    private boolean sentExpirationNotify;


    public Poll() {}

    @Override
    public String toString() {
        return "Poll{" +
                "pollID=" + pollID +
                ", owner=" + owner +
                ", isPrivate=" + isPrivate +
                ", pin=" + pin +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", yesCount=" + yesCount +
                ", noCount=" + noCount +
                ", question='" + question + '\'' +
                ", status=" + status +
                '}';
    }

    public Poll(boolean isPrivate, int pin, LocalDateTime startTime, LocalDateTime endTime, int yesCount, int noCount, String question, Status status) {
        this.isPrivate = isPrivate;
        this.pin = pin;
        this.startTime = startTime;
        this.endTime = endTime;
        this.yesCount = yesCount;
        this.noCount = noCount;
        this.question = question;
        this.status = status;
        sendController = new MessageSendController(rabbitTemplate);
        sentExpirationNotify = false;
    }

    public void setPollID(long pollID) {
        this.pollID = pollID;
    }

    public Agent getOwner() {
        return owner;
    }

    public void setOwner(Agent owner) {
        this.owner = owner;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public long getPollID() {
        return pollID;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getYesCount() {
        return yesCount;
    }

    public void setYesCount(int yesCount) {
        this.yesCount = yesCount;
    }

    public int getNoCount() {
        return noCount;
    }

    public void setNoCount(int noCount) {
        this.noCount = noCount;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void expirationNotify() {
        //MessageSendController sendController = new MessageSendController(rabbitTemplate);
        //send as json object as string?
        int yesCount = this.getYesCount();
        int noCount = this.getNoCount();
        String question = this.getQuestion();
        String message = question + " " + yesCount + " " + noCount;
        sendController.sendPollFinishMessage(message);

    }

    public void setStatus() {
        if (this.startTime.isBefore(LocalDateTime.now()) && this.endTime.isAfter(LocalDateTime.now())) {
            this.status = Status.ACTIVE;
        } else if (this.startTime.isAfter(LocalDateTime.now()) && this.endTime.isAfter(LocalDateTime.now())) {
            this.status = Status.FUTURE;
        } else {
            this.status = Status.EXPIRED;
            //if (!sentExpirationNotification)
            expirationNotify();
            //sentExpirationNotification = true;
        }
    }
    public Status getStatus() {
        this.setStatus();
        return this.status;
    }
}

