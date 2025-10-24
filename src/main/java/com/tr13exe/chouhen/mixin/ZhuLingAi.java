package com.tr13exe.chouhen.mixin;

import com.tr13exe.chouhen.ZhuLIngGuoLu;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.security.auth.callback.Callback;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Mixin(PiglinAi.class)
public class ZhuLingAi
{
    private static final double FANWEI = 16.0D;
    @Inject(method = "findNearestValidAttackTarget", at = @At("RETURN"), cancellable = true)
    private static void helperForPiglinAcquireTargets(Piglin p_35001_, CallbackInfoReturnable<Optional<? extends LivingEntity>> cir)
    {
        if (cir.getReturnValue().isPresent()) {return;}
        var aabb = p_35001_.getBoundingBox().inflate(FANWEI);
        List<LivingEntity> QIANZAIMUBIAO = p_35001_.level().getEntitiesOfClass(LivingEntity.class, aabb, (target)->
        {
            return p_35001_ != target && Sensor.isEntityAttackable(p_35001_, target) && ZhuLIngGuoLu.targetShouldAttacked(p_35001_, target);
        });
        LivingEntity nearestLivingEntityToPiglin = null ;
        double nearestDistance = Double.MAX_VALUE;
        for (LivingEntity MUBIAO:QIANZAIMUBIAO)
        {
            double JULI = MUBIAO.distanceToSqr(p_35001_);
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
