package com.bumptech.glide.manager;

import android.support.annotation.NonNull;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Holds the set of {@link Target}s currently active for a
 * {@link com.bumptech.glide.RequestManager} and forwards on lifecycle events.
 */
public final class TargetTracker implements LifecycleListener {

  //WeakHashMap 的key为 WeakReference，下次GC直接回收
  private final Set<Target<?>> targets =
      Collections.newSetFromMap(new WeakHashMap<Target<?>, Boolean>());

  public void track(@NonNull Target<?> target) {
    targets.add(target);
  }

  public void untrack(@NonNull Target<?> target) {
    targets.remove(target);
  }

  @Override
  public void onStart() {
    for (Target<?> target : Util.getSnapshot(targets)) {
      target.onStart();
    }
  }

  @Override
  public void onStop() {
    for (Target<?> target : Util.getSnapshot(targets)) {
      target.onStop();
    }
  }

  @Override
  public void onDestroy() {
    for (Target<?> target : Util.getSnapshot(targets)) {
      target.onDestroy();
    }
  }

  @NonNull
  public List<Target<?>> getAll() {
    return Util.getSnapshot(targets);
  }

  public void clear() {
    targets.clear();
  }
}
