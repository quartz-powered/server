package org.quartzpowered.engine.object.component;

import lombok.Getter;
import org.quartzpowered.engine.entity.EntityManager;
import org.quartzpowered.engine.object.Component;
import org.quartzpowered.engine.object.annotation.MessageHandler;
import org.quartzpowered.engine.object.annotation.Property;
import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.network.session.profile.PlayerProfile;
import org.quartzpowered.protocol.data.Gamemode;
import org.quartzpowered.protocol.data.info.PlayerInfo;
import org.quartzpowered.protocol.data.info.PlayerInfoAction;
import org.quartzpowered.protocol.data.metadata.Metadata;
import org.quartzpowered.protocol.packet.play.client.PlayerInfoPacket;
import org.quartzpowered.protocol.packet.play.client.SpawnPlayerPacket;

import javax.inject.Inject;
import java.util.Arrays;

public class Player extends Component {
    @Inject private EntityManager entityManager;

    @Getter
    @Property
    private PlayerProfile profile;

    private int entityId;

    @Getter
    private final Metadata metadata = new Metadata();

    public void setProfile(PlayerProfile profile) {
        this.profile = profile;

        // todo respawn player with different profile etc
    }

    @MessageHandler
    public void init() {
        entityId = entityManager.nextEntityId();
        metadata.setByte(10, 0xff);
    }

    @MessageHandler
    public void startObserving(Observer observer) {
        Transform transform = gameObject.getTransform();

        PlayerInfo info = new PlayerInfo();
        info.setProfile(profile);
        info.setGamemode(Gamemode.SURVIVAL);

        PlayerInfoPacket infoPacket = new PlayerInfoPacket();
        infoPacket.setAction(PlayerInfoAction.ADD);
        infoPacket.setInfo(Arrays.asList(info));

        SpawnPlayerPacket spawnPacket = new SpawnPlayerPacket();
        spawnPacket.setEntityId(entityId);
        spawnPacket.setPlayerId(profile.getUniqueId());
        spawnPacket.setPosition(transform.getPosition());
        spawnPacket.setRotation(transform.getRotation());
        spawnPacket.setHeldItem(0);
        spawnPacket.setMetadata(metadata);

        gameObject.observe(infoPacket);
        gameObject.observe(spawnPacket);
    }

    @MessageHandler
    public void stopObserving(Observer observer) {

    }
}
