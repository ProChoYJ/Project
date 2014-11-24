#include <string.h>
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <termios.h>
#include <sys/mman.h>
#include <errno.h>

jint Java_ac_kr_kgu_esproject_ArrayAdderActivity_Checkresult(JNIEnv* env, jobject obj, jintArray A, jint res) {
	int fd, ret;		//부저
	int result = res;	//결과값
	int i, sum = 0;
	int tmp = 0;
	jsize size = (*env) -> GetArrayLength(env, A);
	jint *array = (*env) -> GetIntArrayElements(env, A, 0);
	for(i = 0; i < size; i++) {
		sum += array[i];
	}
	if(sum == result)
		tmp = 1;
	else
		tmp = 0;

	(*env) ->ReleaseIntArrayElements(env, A, array, 0);
	return tmp;
}
