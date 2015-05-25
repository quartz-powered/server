package org.quartzpowered.network.codec;

public interface CodecFactory {
    public FrameCodec createFrameCodec();
    public PacketCodec createPacketCodec();
}
