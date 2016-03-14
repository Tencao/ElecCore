package elec332.core.client.model.model;

import com.google.common.collect.ImmutableList;
import elec332.core.client.model.ElecModelBakery;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Created by Elec332 on 21-11-2015.
 */
@SuppressWarnings("deprecation")
@SideOnly(Side.CLIENT)
public abstract class AbstractItemModel implements IItemModel {

    protected static final ItemCameraTransforms DEFAULT_ITEM_TRANSFORM;
    protected static final ImmutableList<BakedQuad> EMPTY_LIST;

    @Override
    public List<BakedQuad> func_188616_a(IBlockState p_188616_1_, EnumFacing p_188616_2_, long p_188616_3_) {
        return p_188616_2_ == null ? getGeneralQuads() : EMPTY_LIST;
    }

    public abstract List<BakedQuad> getGeneralQuads();

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public final boolean func_188618_c() {
        return isBuiltInRenderer();
    }

    //@Override
    public final boolean isBuiltInRenderer() {
        return isItemTESR();
    }

    @Override
    public ItemOverrideList func_188617_f() {
        return null;
    }

    public boolean isItemTESR(){
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(getTextureLocation().toString());
    }

    public abstract ResourceLocation getTextureLocation();

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return DEFAULT_ITEM_TRANSFORM;
    }

    static {
        DEFAULT_ITEM_TRANSFORM = ElecModelBakery.DEFAULT_ITEM;
        EMPTY_LIST = ImmutableList.of();
    }

    protected static Vector3f applyTranslationScale(Vector3f vec){
        vec.scale(0.0625F);
        return vec;
    }

}
