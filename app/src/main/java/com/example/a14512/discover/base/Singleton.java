package com.example.a14512.discover.base;


/**
 * 单例模板,所有单利实现都继承此类
 * @param <T>
 * @param <P>
 */
public abstract class Singleton<T, P> {
    /**
     * 唯一实例
     */
    private volatile T mInstance;

    /**
     * 创建实例
     * @param p
     * 创建实例所需指定的参数
     * exp: Context context
     * @return
     * 实例
     */
    protected abstract T create(P p);

    /**
     * 获取实例
     * @param p
     * 创建实例时指定的参数
     * exp: Context context
     * @return
     * 实例
     */
    public final T get(P p) {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null) {
                    mInstance = create(p);
                }
            }
        }
        return mInstance;
    }
}
