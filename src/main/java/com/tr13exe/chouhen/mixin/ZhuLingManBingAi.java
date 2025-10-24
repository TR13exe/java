package com.tr13exe.chouhen.mixin;

import com.tr13exe.chouhen.ZhuLIngGuoLu;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBruteAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(PiglinBruteAi.class)
public class ZhuLingManBingAi
{
    private static final double FANWEI = 16.0D;
    @Inject(method = "findNearestValidAttackTarget", at = @At("RETURN"), cancellable = true)
    private static void helperForPiglinAcquireTargets(AbstractPiglin p_35087_, CallbackInfoReturnable<Optional<? extends LivingEntity>> cir)
    {
        if (cir.getReturnValue().isPresent()) {return;}
        var aabb = p_35087_.getBoundingBox().inflate(FANWEI);
        List<LivingEntity> QIANZAIMUBIAO = p_35087_.level().getEntitiesOfClass(LivingEntity.class, aabb,(target)->
        {
            return p_35087_ != target && Sensor.isEntityAttackable(p_35087_, target) && ZhuLIngGuoLu.targetShouldAttacked(p_35087_, target);
        });
        LivingEntity nearestLivingEntityToPiglin = null ;
        double nearestDistance = Double.MAX_VALUE;
        for (LivingEntity MUBIAO:QIANZAIMUBIAO)
        {
            double JULI = MUBIAO.distanceToSqr(p_35087_);
            if (JULI < nearestDistance)
            {
                nearestDistance = JULI;
                nearestLivingEntityToPiglin = MUBIAO;
            }
        }
        if (nearestLivingEntityToPiglin != null)
        {
            cir.setReturnValue(Optional.of(nearestLivingEntityToPiglin));
        }
    }
}
