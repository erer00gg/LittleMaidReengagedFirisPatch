package net.blacklab.lmr.client.renderer.layer;

import firis.lmlib.api.caps.IModelConfigCompound;
import firis.lmlib.api.client.renderer.LMRenderMultiModel;
import firis.lmlib.api.client.renderer.layer.LMLayerArmorBase;
import net.blacklab.lmr.entity.maidmodel.IMultiModelEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * GUIの防具Layer
 * @author firis-games
 *
 */
@SideOnly(Side.CLIENT)
public class LayerArmorLittleMaidGui extends LMLayerArmorBase {

	public LayerArmorLittleMaidGui(LMRenderMultiModel<? extends EntityLiving> rendererIn) {
		
		super(rendererIn);
		
	}

	@Override
	protected IModelConfigCompound getArmorModelConfigCompound(EntityLivingBase entityLivingBaseIn, float limbSwing,
			float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale,
			EntityEquipmentSlot slot) {
		IMultiModelEntity modelEntity = (IMultiModelEntity) entityLivingBaseIn;
		IModelConfigCompound modelConfigCompound = modelEntity.getModelConfigCompound();
		return modelConfigCompound;
	}
	
}
