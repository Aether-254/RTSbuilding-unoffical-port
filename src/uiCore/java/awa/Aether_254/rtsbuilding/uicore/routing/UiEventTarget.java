package awa.Aether_254.rtsbuilding.uicore.routing;

import awa.Aether_254.rtsbuilding.uicore.event.UiEventReply;
import awa.Aether_254.rtsbuilding.uicore.event.UiKeyEvent;
import awa.Aether_254.rtsbuilding.uicore.event.UiPointerEvent;

/** 统一路由器调用的最小平台无关目标接口。 */
public interface UiEventTarget {
    UiEventReply handlePointer(UiPointerEvent event);

    UiEventReply handleKey(UiKeyEvent event);

    /** 返回 true 表示本层已响应 Escape 并关闭或取消。 */
    boolean handleEscape();
}
