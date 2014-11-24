/*
 * Copyright (C) 2009 Hanback Electronics Inc.
 *
 */
#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <termios.h>
#include <sys/mman.h>
#include <errno.h>


jint
Java_ac_kr_kgu_esproject_ArrayAdderActivity_BuzzerControl( JNIEnv* env,
                                                  jobject thiz, jint value)
{
	int fd,ret;
	int data = value;
	int i,count=0,scount=0;
	int on=1,off=0;
	
	fd = open("/dev/buzzer",O_WRONLY);
  
	if(fd < 0) return -errno;
	
	if(data==0){
		for(i=0;i<3;i++){
			ret = write(fd, &on, 1);
			
			sleep(1);
			
			ret = write(fd, &off, 1);
			
			sleep(2);
		}
	}
	else if(data==1){
		ret = write(fd, &on, 1);
	}
	else
		ret = write(fd, &off, 1);
	close(fd);
  
	if(ret == 1) return 0;
  	
	return -1;
}
