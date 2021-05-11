package com.wdjk.webdemo624.utils;

import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.GitHubAvatar;
import com.talanlabs.avatargenerator.IdenticonAvatar;
import com.talanlabs.avatargenerator.TriangleAvatar;
import com.talanlabs.avatargenerator.cat.CatAvatar;
import com.talanlabs.avatargenerator.eightbit.EightBitAvatar;
import com.talanlabs.avatargenerator.layers.backgrounds.ColorPaintBackgroundLayer;
import com.talanlabs.avatargenerator.layers.backgrounds.RandomColorPaintBackgroundLayer;
import com.talanlabs.avatargenerator.layers.masks.RoundRectMaskLayer;
import com.talanlabs.avatargenerator.layers.others.RandomColorPaintLayer;
import com.talanlabs.avatargenerator.layers.others.ShadowLayer;
import com.wdjk.webdemo624.WebDemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.io.File;
import java.util.Random;

/**
 * @program: webDemo
 * @description
 * @author: zhuhua
 * @create: 2021-05-09 18:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = WebDemoApplication.class)
public class AvatarCreator {

    @Test
    public void avatarCreator() {
        Avatar triangleAvatar = TriangleAvatar.newAvatarBuilder().build();
        Avatar cat = CatAvatar.newAvatarBuilder().layers(new ShadowLayer(),
                new RandomColorPaintBackgroundLayer()
                ,new RoundRectMaskLayer())
                .padding(8)
                .margin(8)
                .build();
        Avatar identIcon = IdenticonAvatar.newAvatarBuilder().build();
        Avatar githubIcon = GitHubAvatar.newAvatarBuilder().layers(new ColorPaintBackgroundLayer(Color.WHITE)).build();
        Avatar eigBitM = EightBitAvatar.newMaleAvatarBuilder().build();
        Avatar eigBitFM = EightBitAvatar.newFemaleAvatarBuilder().build();
        Avatar []avatars = {triangleAvatar,cat,identIcon,githubIcon,eigBitFM,eigBitM};
        int index;
        long code ;
        String fileLocation = "./avatars/";
        String avatarFileName = "1";
        Random random =new Random(545645);

        for(long i = 0;i<=1000;i++){
            code = Math.abs(random.nextLong());
            index =(int) (Math.random()*avatars.length);
            avatarFileName +=i;
            File file = new File(fileLocation+avatarFileName+".png");
            avatars[index].createAsPngToFile(code,file);
            avatarFileName = "";
        }
    }

}
