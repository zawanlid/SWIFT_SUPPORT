	
	
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(1, 'bulletin 1001','bulletin body 1001. start with hello!', 10, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(2, 'bulletin 1002','bulletin body 1002. start with hello!', 0, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(3, 'bulletin 1003','bulletin body 1003. start with hello!', 9, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(4, 'bulletin 1004','bulletin body 1004. start with hello!', 2, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(5, 'bulletin 1005','bulletin body 1005. start with hello!', 1, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(6, 'bulletin 1006','bulletin body 1006. start with hello!', 0, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(7, 'bulletin 1007','bulletin body 1007. start with hello!', 6, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(8, 'bulletin 1008','bulletin body 1008. start with hello!', 4, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(9, 'bulletin 1009','bulletin body 1009. start with hello!', 3, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(10, 'bulletin 1010','bulletin body 1010. start with hello!', 8, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(11, 'bulletin 1011','bulletin body 1011. start with hello!', 20, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(12, 'bulletin 1012','bulletin body 1012. start with hello!', 1, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(13, 'bulletin 1013','bulletin body 1013. start with hello!', 5, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(14, 'bulletin 1014','bulletin body 1014. start with hello!', 8, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	INSERT INTO bulletins (id,title,body,read_count,created_on,userid,password,name,email) 
	VALUES(15, 'bulletin 1015','bulletin body 1015. start with hello!', 0, CURRENT_TIMESTAMP,'j2ee','j2ee','J2EE','j2ee@java.com');
	
	
	INSERT INTO bulletin_replies (id,name,body,created_on,bulletin_id) VALUES(1, 'Dil Nawaz','this is my first reply to this bulletin',CURRENT_TIMESTAMP,1);
	INSERT INTO bulletin_replies (id,name,body,created_on,bulletin_id) VALUES(2, 'Jeff','this is jeff here',CURRENT_TIMESTAMP,1);
	
	INSERT INTO sequence VALUES('BULLETIN_ID', 100);
	INSERT INTO sequence VALUES('BULLETIN_REPLY_ID', 100);
