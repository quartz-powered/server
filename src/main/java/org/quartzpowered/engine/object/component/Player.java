package org.quartzpowered.engine.object.component;

import lombok.Getter;
import org.quartzpowered.engine.object.Component;
import org.quartzpowered.engine.object.annotation.MessageHandler;
import org.quartzpowered.engine.object.annotation.Property;
import org.quartzpowered.engine.observe.Observer;
import org.quartzpowered.network.protocol.packet.Packet;
import org.quartzpowered.network.session.profile.PlayerProfile;

public class Player extends Component {
    @Getter
    @Property
    private PlayerProfile profile;

    public void setProfile(PlayerProfile profile) {
        this.profile = profile;

        // todo respawn player with different profile etc
    }

    @MessageHandler
    public void startObserving(Observer observer) {

    }

    @MessageHandler
    public void stopObserving(Observer observer) {

    }
}
