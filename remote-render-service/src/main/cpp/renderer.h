#ifndef RENDERER_H
#define RENDERER_H

#include <pthread.h>
#include <EGL/egl.h>
#include <GLES/gl.h>


class Renderer {

public:
    Renderer();
    virtual ~Renderer();
    void start();
    void stop();
    void setWindow(ANativeWindow* window);
    
    
private:

    enum RenderThreadMessage {
        MSG_NONE = 0,
        MSG_WINDOW_SET,
        MSG_RENDER_LOOP_EXIT
    };

    pthread_t _threadId;
    pthread_mutex_t _mutex;
    enum RenderThreadMessage _msg;

    ANativeWindow* _window;

    EGLDisplay _display;
    EGLSurface _surface;
    EGLContext _context;
    GLfloat _angle;

    void renderLoop();
    bool initialize();
    void destroy();
    void drawFrame();

    static void* threadStartCallback(void *myself);

};

#endif // RENDERER_H
