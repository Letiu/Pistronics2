package letiu.pistronics.packets;

import cpw.mods.fml.relauncher.Side;
import letiu.modbase.util.WorldUtil;
import letiu.pistronics.blocks.BGear;
import letiu.pistronics.data.PTile;
import letiu.pistronics.tiles.TileGear;
import letiu.pistronics.tiles.TileStatue;
import letiu.pistronics.util.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StatueDataPacket extends LocationPacket<StatueDataPacket> {

    private boolean camou;
    private int camouID, camouMeta, scale, angle, resolution;

    public StatueDataPacket() {

    }

    public StatueDataPacket(TileStatue tile, int dimID) {
        this.x = tile.tileEntity.xCoord;
        this.y = tile.tileEntity.yCoord;
        this.z = tile.tileEntity.zCoord;
        this.dimID = dimID;

        this.camou = tile.camou;
        this.camouID = tile.camouID;
        this.camouMeta = tile.camouMeta;
        this.scale = tile.getScale();
        this.angle = tile.getAngle();
        this.resolution = tile.getStatueResolution();
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        super.write(out);
        out.writeBoolean(camou);
        out.writeInt(camouID);
        out.writeInt(camouMeta);
        out.writeInt(scale);
        out.writeInt(angle);
        out.writeInt(resolution);
    }

    @Override
    public void read(DataInputStream in) throws IOException {
        super.read(in);
        this.camou = in.readBoolean();
        this.camouID = in.readInt();
        this.camouMeta = in.readInt();
        this.scale = in.readInt();
        this.angle = in.readInt();
        this.resolution = in.readInt();
    }

    @Override
    public void execute(StatueDataPacket packet, EntityPlayer player, Side side) {
        if (side == Side.CLIENT && packet.dimID == player.dimension) {

            this.x = packet.x;
            this.y = packet.y;
            this.z = packet.z;
            World world = player.worldObj;

            PTile pTile = WorldUtil.getPTile(world, x, y, z);
            if (pTile != null && pTile instanceof TileStatue) {
                TileStatue tile = (TileStatue) pTile;
                tile.camou = packet.camou;
                tile.camouID = packet.camouID;
                tile.camouMeta = packet.camouMeta;
                tile.setScale(packet.scale);
                tile.setAngle(packet.angle);
                tile.setStatueResolution(packet.resolution);
            }
        }
    }
}
