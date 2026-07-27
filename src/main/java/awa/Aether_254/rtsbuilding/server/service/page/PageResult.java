package awa.Aether_254.rtsbuilding.server.service.page;

import awa.Aether_254.rtsbuilding.network.storage.S2CRtsStoragePagePayload;

/**
 * 储存浏览器页面构建结果。
 *
 * <p>包含已构建的页面数据包和经过边界裁剪的实际页面索引（{@code safePage}）。
 *
 * @param payload  发送给客户端的完整页面数据包
 * @param safePage 经过边界限定的实际页面索引（0 到 totalPages-1 之间）
 */
public record PageResult(S2CRtsStoragePagePayload payload, int safePage) {
}
