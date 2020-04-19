#include <stdint.h>
#include <jni.h>
#include <android/native_window.h> // requires ndk r5 or newer
#include <android/native_window_jni.h> // requires ndk r5 or newer

#include "jniapi.h"
#include "logger.h"
#include "renderer.h"

#define LOG_TAG "RemoteRender_native"


static ANativeWindow *window = 0;
static Renderer *renderer = 0;

extern "C" JNIEXPORT void JNICALL Java_com_remote_render_service_RemoteRenderService_nativeOnStart(JNIEnv* jenv,
                                                                                                  jclass obj)
{
    LOG_INFO("nativeOnStart");
    renderer = new Renderer();
    return;
}

extern "C" JNIEXPORT void JNICALL Java_com_remote_render_service_RemoteRenderService_nativeOnResume(JNIEnv* jenv,
                                                                                                   jclass obj)
{
    LOG_INFO("nativeOnResume");
    renderer->start();
    return;
}

extern "C" JNIEXPORT void JNICALL Java_com_remote_render_service_RemoteRenderService_nativeOnPause(JNIEnv* jenv,
                                                                                                  jclass obj)
{
    LOG_INFO("nativeOnPause");
    renderer->stop();
    return;
}

extern "C" JNIEXPORT void JNICALL Java_com_remote_render_service_RemoteRenderService_nativeOnStop(JNIEnv* jenv,
                                                                                                 jclass obj)
{
    LOG_INFO("nativeOnStop");
    delete renderer;
    renderer = 0;
    return;
}

extern "C" JNIEXPORT void JNICALL Java_com_remote_render_service_RemoteRenderService_nativeSetSurface(JNIEnv* jenv,
                                                                                                     jclass obj, jobject surface)
{
    if (surface != 0) {
        window = ANativeWindow_fromSurface(jenv, surface);
        LOG_INFO("Got window %p", window);
        renderer->setWindow(window);
    } else {
        LOG_INFO("Releasing window");
        ANativeWindow_release(window);
    }

    return;
}

