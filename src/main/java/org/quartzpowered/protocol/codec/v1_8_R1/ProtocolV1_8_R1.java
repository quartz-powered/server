/**
 * This file is a component of Quartz Powered, this license makes sure any work
 * associated with Quartz Powered, must follow the conditions of the license included.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Quartz Powered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.quartzpowered.protocol.codec.v1_8_R1;

import org.quartzpowered.network.protocol.codec.NoopCodec;
import org.quartzpowered.protocol.codec.indentifier.IdentifierProtocol;
import org.quartzpowered.protocol.codec.indentifier.common.client.KickCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.common.client.CompressionCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.client.EncryptionRequestCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.client.LoginResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.server.EncryptionResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.login.server.LoginRequestCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.play.client.*;
import org.quartzpowered.protocol.codec.v1_8_R1.play.server.*;
import org.quartzpowered.protocol.codec.v1_8_R1.play.shared.CloseWindowCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.play.shared.ConfirmTransactionCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.play.shared.KeepAliveCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.play.shared.PluginMessageCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.client.PongCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.client.StatusResponseCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.server.PingCodec;
import org.quartzpowered.protocol.codec.v1_8_R1.status.server.StatusRequestCodec;
import org.quartzpowered.protocol.packet.common.client.CompressionPacket;
import org.quartzpowered.protocol.packet.common.client.KickPacket;
import org.quartzpowered.protocol.packet.login.client.EncryptionRequestPacket;
import org.quartzpowered.protocol.packet.login.client.LoginResponsePacket;
import org.quartzpowered.protocol.packet.login.server.EncryptionResponsePacket;
import org.quartzpowered.protocol.packet.login.server.LoginRequestPacket;
import org.quartzpowered.protocol.packet.play.client.*;
import org.quartzpowered.protocol.packet.play.server.*;
import org.quartzpowered.protocol.packet.play.shared.*;
import org.quartzpowered.protocol.packet.status.client.PongPacket;
import org.quartzpowered.protocol.packet.status.client.StatusResponsePacket;
import org.quartzpowered.protocol.packet.status.server.PingPacket;
import org.quartzpowered.protocol.packet.status.server.StatusRequestPacket;

import static org.quartzpowered.network.protocol.ProtocolState.*;

public class ProtocolV1_8_R1 extends IdentifierProtocol {
    @Override
    public String getName() {
        return "1.8";
    }

    @Override
    public int getVersion() {
        return 47;
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();

        serverBoundPacket(STATUS, 0x00, StatusRequestPacket.class, new StatusRequestCodec());
        serverBoundPacket(STATUS, 0x01, PingPacket.class, new PingCodec());

        clientBoundPacket(STATUS, 0x00, StatusResponsePacket.class, new StatusResponseCodec());
        clientBoundPacket(STATUS, 0x01, PongPacket.class, new PongCodec());

        serverBoundPacket(LOGIN, 0x00, LoginRequestPacket.class, new LoginRequestCodec());
        serverBoundPacket(LOGIN, 0x01, EncryptionResponsePacket.class, new EncryptionResponseCodec());

        clientBoundPacket(LOGIN, 0x01, EncryptionRequestPacket.class, new EncryptionRequestCodec());
        clientBoundPacket(LOGIN, 0x02, LoginResponsePacket.class, new LoginResponseCodec());
        clientBoundPacket(LOGIN, 0x03, CompressionPacket.class, new CompressionCodec());


        serverBoundPacket(PLAY, 0x00, KeepAlivePacket.class, new KeepAliveCodec());
        serverBoundPacket(PLAY, 0x01, PlayerChatMessagePacket.class, new PlayerChatMessageCodec());
        serverBoundPacket(PLAY, 0x03, PlayerPacket.class, new PlayerCodec());
        serverBoundPacket(PLAY, 0x04, PlayerPositionPacket.class, new PlayerPositionCodec());
        serverBoundPacket(PLAY, 0x05, PlayerLookPacket.class, new PlayerLookCodec());
        serverBoundPacket(PLAY, 0x06, PlayerPositionLookPacket.class, new PlayerPositionLookCodec());
        serverBoundPacket(PLAY, 0x07, PlayerActionPacket.class, new PlayerActionCodec());
        serverBoundPacket(PLAY, 0x08, PlayerBlockPlacementPacket.class, new PlayerBlockPlacementCodec());
        serverBoundPacket(PLAY, 0x09, HeldItemChangePacket.class, new HeldItemChangeCodec());
        serverBoundPacket(PLAY, 0x0A, PlayerAnimationPacket.class, new NoopCodec<>());
        serverBoundPacket(PLAY, 0x0B, EntityActionPacket.class, new EntityActionCodec());
//        serverBoundPacket(PLAY, 0x0C, SteerVehiclePacket.class, new SteerVehicleCodec());
        serverBoundPacket(PLAY, 0x0D, CloseWindowPacket.class, new CloseWindowCodec());
//        serverBoundPacket(PLAY, 0x0E, ClickWindowPacket.class, new ClickWindowCodec());
        serverBoundPacket(PLAY, 0x0F, ConfirmTransactionPacket.class, new ConfirmTransactionCodec());

//        serverBoundPacket(PLAY, 0x10, CreativeInventoryActionPacket.class, new CreativeInventoryActionCodec());
//        serverBoundPacket(PLAY, 0x11, EnchantItemPacket.class, new EnchantItemCodec());
//        serverBoundPacket(PLAY, 0x12, UpdateSignPacket.class, new UpdateSignCodec());
        serverBoundPacket(PLAY, 0x13, PlayerAbilitiesPacket.class, new PlayerAbilitiesCodec());
//        serverBoundPacket(PLAY, 0x14, TabCompletePacket.class, new TabCompleteCodec());
        serverBoundPacket(PLAY, 0x15, ClientSettingsPacket.class, new ClientSettingsCodec());
        serverBoundPacket(PLAY, 0x16, ClientStatusPacket.class, new ClientStatusCodec());
        serverBoundPacket(PLAY, 0x17, PluginMessagePacket.class, new PluginMessageCodec());
//        serverBoundPacket(PLAY, 0x18, SpectatePacket.class, new SpectateCodec());
        serverBoundPacket(PLAY, 0x19, ResourcePackStatusPacket.class, new ResourcePackStatusCodec());


        clientBoundPacket(PLAY, 0x00, KeepAlivePacket.class, new KeepAliveCodec());
        clientBoundPacket(PLAY, 0x01, JoinGamePacket.class, new JoinGameCodec());
        clientBoundPacket(PLAY, 0x02, ChatMessagePacket.class, new ChatMessageCodec());
        clientBoundPacket(PLAY, 0x03, TimeUpdatePacket.class, new TimeUpdateCodec());
//        clientBoundPacket(PLAY, 0x04, EntityEquipmentPacket.class, new EntityEquipmentCodec());
        clientBoundPacket(PLAY, 0x05, SpawnPositionPacket.class, new SpawnPositionCodec());
        clientBoundPacket(PLAY, 0x06, UpdateHealthPacket.class, new UpdateHealthCodec());
        clientBoundPacket(PLAY, 0x07, RespawnPacket.class, new RespawnCodec());
        clientBoundPacket(PLAY, 0x08, PlayerTeleportPacket.class, new PlayerTeleportCodec());
        clientBoundPacket(PLAY, 0x09, HeldItemChangePacket.class, new PlayerHeldItemChangeCodec());
//        clientBoundPacket(PLAY, 0x0A, UseBedPacket.class, new UseBedCodec());
        clientBoundPacket(PLAY, 0x0B, AnimationPacket.class, new AnimationCodec());
        clientBoundPacket(PLAY, 0x0C, SpawnPlayerPacket.class, new SpawnPlayerCodec());
        clientBoundPacket(PLAY, 0x0D, CollectItemPacket.class, new CollectItemCodec());
//        clientBoundPacket(PLAY, 0x0E, SpawnObjectPacket.class, new SpawnObjectCodec());
//        clientBoundPacket(PLAY, 0x0F, SpawnMobPacket.class, new SpawnMobCodec());

//        clientBoundPacket(PLAY, 0x10, SpawnPaintingPacket.class, new SpawnPaintingCodec());
//        clientBoundPacket(PLAY, 0x11, SpawnExperiencePacket.class, new SpawnExperienceCodec());
        clientBoundPacket(PLAY, 0x12, EntityVelocityPacket.class, new EntityVelocityCodec());
        clientBoundPacket(PLAY, 0x13, EntityDestroyPacket.class, new EntityDestroyCodec());
        clientBoundPacket(PLAY, 0x14, EntityPacket.class, new EntityCodec());
        clientBoundPacket(PLAY, 0x15, EntityMovePacket.class, new EntityMoveCodec());
        clientBoundPacket(PLAY, 0x16, EntityLookPacket.class, new EntityLookCodec());
        clientBoundPacket(PLAY, 0x17, EntityLookMovePacket.class, new EntityLookMoveCodec());
        clientBoundPacket(PLAY, 0x18, EntityTeleportPacket.class, new EntityTeleportCodec());
        clientBoundPacket(PLAY, 0x19, EntityHeadLookPacket.class, new EntityHeadLookCodec());
        clientBoundPacket(PLAY, 0x1A, EntityStatusPacket.class, new EntityStatusCodec());
        clientBoundPacket(PLAY, 0x1B, AttachEntityPacket.class, new AttachEntityCodec());
        clientBoundPacket(PLAY, 0x1C, EntityMetadataPacket.class, new EntityMetadataCodec());
//        clientBoundPacket(PLAY, 0x1D, EntityEffectPacket.class, new EntityEffectCodec());
        clientBoundPacket(PLAY, 0x1E, RemoveEntityEffectPacket.class, new RemoveEntityEffectCodec());
        clientBoundPacket(PLAY, 0x1F, SetExperiencePacket.class, new SetExperienceCodec());

//        clientBoundPacket(PLAY, 0x20, EntityPropertiesPacket.class, new EntityPropertiesCodec());
        clientBoundPacket(PLAY, 0x21, ChunkPacket.class, new ChunkCodec());
//        clientBoundPacket(PLAY, 0x22, MultiBlockChangePacket.class, new MultiBlockChangeCodec());
//        clientBoundPacket(PLAY, 0x23, BlockChangePacket.class, new BlockChangeCodec());
//        clientBoundPacket(PLAY, 0x24, BlockActionPacket.class, new BlockActionCodec());
//        clientBoundPacket(PLAY, 0x25, BlockBreakAnimationPacket.cass, new BlockBreakAnimationCodec());
        clientBoundPacket(PLAY, 0x26, ChunkBulkPacket.class, new ChunkBulkCodec());
        clientBoundPacket(PLAY, 0x27, ExplosionPacket.class, new ExplosionCodec());
//        clientBoundPacket(PLAY, 0x28, EffectPacket.class, new EffectCodec());
//        clientBoundPacket(PLAY, 0x29, SoundEffectPacket.class, new SoundEffectCodec());
        clientBoundPacket(PLAY, 0x2A, ParticlePacket.class, new ParticleCodec());
        clientBoundPacket(PLAY, 0x2B, ChangeGameStatePacket.class, new ChangeGameStateCodec());
//        clientBoundPacket(PLAY, 0x2C, SpawnGlobalEntityPacket.class, new SpawnGlobalEntityCodec());
        clientBoundPacket(PLAY, 0x2D, OpenWindowPacket.class, new OpenWindowCodec());
        clientBoundPacket(PLAY, 0x2E, CloseWindowPacket.class, new CloseWindowCodec());
        clientBoundPacket(PLAY, 0x2F, SetExperiencePacket.class, new SetExperienceCodec());

        clientBoundPacket(PLAY, 0x30, WindowItemsPacket.class, new WindowItemsCodec());
//        clientBoundPacket(PLAY, 0x31, WindowPropertyPacket.class, new WindowPropertyCodec());
        clientBoundPacket(PLAY, 0x32, ConfirmTransactionPacket.class, new ConfirmTransactionCodec());
//        clientBoundPacket(PLAY, 0x33, UpdateSignPacket.class, new UpdateSignCodec());
//        clientBoundPacket(PLAY, 0x34, MapsPacket.class, new MapsCodec());
//        clientBoundPacket(PLAY, 0x35, UpdateBlockEntityPacket.class, new UpdateBlockEntityCodec());
//        clientBoundPacket(PLAY, 0x36, SignEditorOpenPacket.class, new SignEditorOpenCodec());
        clientBoundPacket(PLAY, 0x37, StatisticsPacket.class, new StatisticsCodec());
        clientBoundPacket(PLAY, 0x38, PlayerInfoPacket.class, new PlayerInfoCodec());
        clientBoundPacket(PLAY, 0x39, PlayerAbilitiesPacket.class, new PlayerAbilitiesCodec());
//        clientBoundPacket(PLAY, 0x3A, TabCompletePacket.class, new TabCompleteCodec());
//        clientBoundPacket(PLAY, 0x3B, ScoreboardObjectivePacket.class, new ScoreboardObjectiveCodec());
//        clientBoundPacket(PLAY, 0x3C, UpdateScorePacket.class, new UpdateScoreCodec());
//        clientBoundPacket(PLAY, 0x3D, DisplayScoreboardPacket.class, new DisplayScoreboardCodec());
//        clientBoundPacket(PLAY, 0x3E, TeamsPacket.class, new TeamsCodec());
        clientBoundPacket(PLAY, 0x3F, PluginMessagePacket.class, new PluginMessageCodec());

        clientBoundPacket(PLAY, 0x40, KickPacket.class, new KickCodec());
        clientBoundPacket(PLAY, 0x41, ServerDifficultyPacket.class, new ServerDifficultyCodec());
//        clientBoundPacket(PLAY, 0x42, CombatEventPacket.class, new CombatEventCodec());
        clientBoundPacket(PLAY, 0x43, CameraPacket.class, new CameraCodec());
//        clientBoundPacket(PLAY, 0x44, WorldBorderPacket.class, new WorldBorderCodec());
        clientBoundPacket(PLAY, 0x45, TitlePacket.class, new TitleCodec());
        clientBoundPacket(PLAY, 0x46, CompressionPacket.class, new CompressionCodec());
//        clientBoundPacket(PLAY, 0x47, PlayerListHeaderFooterPacket.class, new PlayerListHeaderFooterCodec());
//        clientBoundPacket(PLAY, 0x48, ResourcePackSendPacket.class, new ResourcePackSendCodec());
//        clientBoundPacket(PLAY, 0x49, UpdateEntityNBTPacket.class, new UpdateEntityNBTCodec());

    }
}
