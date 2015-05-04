package elec332.core.baseclasses.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elec332.core.api.wrench.IRightClickCancel;
import elec332.core.api.wrench.IWrenchable;
import elec332.core.helper.RegisterHelper;
import elec332.core.util.DirectionHelper;
import elec332.core.util.IComparatorOverride;
import elec332.core.util.IRedstoneHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

/**
 * Created by Elec332 on 30-4-2015.
 */
public class BlockTileBase extends Block implements IWrenchable, ITileEntityProvider{

    public BlockTileBase(Material mat, Class tileClass, String blockName, String modID) {
        super(mat);
        setResistance(4.5F);
        setHardness(2.0F);
        setStepSound(soundTypeStone);
        this.setBlockName(modID + "." + blockName);
        this.tileClass = tileClass;
        this.blockName = blockName;
        this.modID = modID;
    }

    @SideOnly(Side.CLIENT)
    public IIcon[] icons = new IIcon[6];
    private Class tileClass;
    private String blockName;
    private String modID;

    public BlockTileBase register(){
        RegisterHelper.registerBlock(this, blockName);
        return this;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IRightClickCancel)
            return false;
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            return ((TileBase) tile).onBlockActivated(player, side, hitX, hitY, hitZ);
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        try {
            return (TileEntity) this.tileClass.newInstance();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ItemStack ItemDropped() {
        return new ItemStack(this);
    }

    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public void onWrenched(World world, int i, int i1, int i2, ForgeDirection forgeDirection) {
        TileEntity tile = world.getTileEntity(i, i1, i2);
        if (tile instanceof TileBase)
            ((TileBase) tile).onWrenched(forgeDirection);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            ((TileBase) tile).onBlockPlacedBy(entityLiving, stack);
        super.onBlockPlacedBy(world, x, y, z, entityLiving, stack);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            ((TileBase) tile).onNeighborBlockChange(block);
        super.onNeighborBlockChange(world, x, y, z, block);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            ((TileBase) tile).onBlockAdded();
        super.onBlockAdded(world, x, y, z);
    }


    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            ((TileBase) tile).onBlockRemoved();
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            return ((TileBase) tile).getLightValue();
        return super.getLightValue(world, x, y, z);
    }

    @Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            return ((TileBase) tile).getLightOpacity();
        return super.getLightOpacity(world, x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileBase)
            return ((TileBase) tile).getDrops(fortune);
        return super.getDrops(world, x, y, z, metadata, fortune);
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof IRedstoneHandler)
            return ((IRedstoneHandler) tile).isProvidingWeakPower(side);
        return super.isProvidingWeakPower(world, x, y, z, side);
    }

    @Override
    public boolean canConnectRedstone(IBlockAccess world, int x, int y, int z, int direction) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof IRedstoneHandler)
            return ((IRedstoneHandler) tile).canConnectRedstone(direction);
        return super.canConnectRedstone(world, x, y, z, direction);
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return IComparatorOverride.class.isAssignableFrom(this.tileClass);
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof IComparatorOverride)
            return ((IComparatorOverride) tile).getComparatorInputOverride(side);
        return super.getComparatorInputOverride(world, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[DirectionHelper.ROTATION_MATRIX_YAW[meta][side]];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister){
        icons[0] = iconRegister.registerIcon(getBottomIconName());
        icons[1] = iconRegister.registerIcon(getTopIconName());
        icons[2] = iconRegister.registerIcon(getFrontTexture());
        icons[3] = iconRegister.registerIcon(getSideTexture(BlockSide.BACK));
        icons[4] = iconRegister.registerIcon(getSideTexture(BlockSide.RIGHT));
        icons[5] = iconRegister.registerIcon(getSideTexture(BlockSide.LEFT));
    }

    public String getSideTexture(BlockSide side) {
        return getTextureName() + "_side";
    }

    public String getFrontTexture(){
        return getTextureName() + "_front";
    }

    public String getTopIconName() {
        return getTextureName() + "_top";
    }

    public String getBottomIconName() {
        return getTextureName() + "_bottom";
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected String getTextureName(){
        if (this.textureName != null)
            return textureName;
        return modID + ":" + blockName;
    }

    protected enum BlockSide{
        LEFT, BACK, RIGHT
    }
}
