CREATE TABLE notifications_log (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   user_id BIGINT NOT NULL,
                                   target_email VARCHAR(100) NOT NULL,
                                   title VARCHAR(150) NOT NULL,
                                   message VARCHAR(1000) NOT NULL,
                                   status ENUM('PENDING','SENT','FAILED') NOT NULL,
                                   created_at DATETIME NOT NULL,
                                   sent_at DATETIME
);