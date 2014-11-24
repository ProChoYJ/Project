# Copyright (C) 2009 Hanback Electronics Inc.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := buzzer2
LOCAL_SRC_FILES := buzzer.c

include $(BUILD_SHARED_LIBRARY)
