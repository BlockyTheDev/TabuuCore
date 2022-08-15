package nl.tabuu.tabuucore.nms.v1_14_R1.container;

import net.minecraft.server.v1_14_R1.Container;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PacketPlayOutCloseWindow;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public abstract class ContainerWindow extends nl.tabuu.tabuucore.nms.wrapper.container.ContainerWindow {

    protected ContainerWindow(Player player) {
        super(player, ((CraftPlayer) player).getHandle().nextContainerCounter());
    }

    protected abstract Container getContainer();

    @Override
    public Inventory getInventory() {
        return getContainer().getBukkitView().getTopInventory();
    }

    @Override
    protected void sendContainerWindowClosePacket() {
        getEntityPlayer().playerConnection.sendPacket(new PacketPlayOutCloseWindow(getId()));
    }

    @Override
    protected void callCloseCurrentInventoryEvent() {
        CraftEventFactory.handleInventoryCloseEvent(getEntityPlayer());
    }

    @Override
    protected void setPlayerWindowToPlayerInventory() {
        getEntityPlayer().activeContainer = getEntityPlayer().defaultContainer;
    }

    @Override
    protected void setPlayWindowToContainerInventory() {
        getEntityPlayer().activeContainer = getContainer();
        getContainer().addSlotListener(getEntityPlayer());
    }

    protected EntityPlayer getEntityPlayer() {
        return ((CraftPlayer) getPlayer()).getHandle();
    }
}