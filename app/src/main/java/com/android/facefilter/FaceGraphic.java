/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.facefilter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;

import com.android.facefilter.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.util.List;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float GENERIC_POS_OFFSET = 20.0f;
    private static final float GENERIC_NEG_OFFSET = -20.0f;

    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;
    private  Bitmap bitmap_head;
    private Bitmap bitmap_glasses,bitmap_bluegoggles;
    private Bitmap bitmap_cap,bitmap_gokuhair,bitmap_gokuhair2,bitmap_hairblonde,bitmap_longhair;
    private Bitmap bitmap_beard,bitmap_beard1,bitmap_beard3;
    private Bitmap bitmap_moustache_glasses,op_moustache_glasses;
    private Bitmap op_head;
    private Bitmap op_glasses,op_bluegoggles;
    private Bitmap op_cap,op_gokuhair,op_gokuhair2,op_hairblonde,op_longhair;
    private Bitmap op_beard,op_beard1,op_beard3;
    FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
        bitmap_head = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.hair);
        bitmap_glasses = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.glasses);
        bitmap_cap = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.cap);
        bitmap_beard = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.beard);
        bitmap_beard1 = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.beard_one);
        bitmap_gokuhair = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.goku_hair);
        bitmap_bluegoggles = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.blue_goggles);
        bitmap_gokuhair2 = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.goku);
        bitmap_hairblonde = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.hair_blonde);
        bitmap_longhair = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.long_hair);
        bitmap_moustache_glasses = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.moustache_glasses);
        bitmap_beard3 = BitmapFactory.decodeResource(getOverlay().getContext().getResources(), R.drawable.beard_three);
        op_head = bitmap_head;
        op_glasses = bitmap_glasses;
        op_cap = bitmap_cap;
        op_beard = bitmap_beard;
        op_beard1 = bitmap_beard1;
        op_gokuhair = bitmap_gokuhair;
        op_bluegoggles = bitmap_bluegoggles;
        op_gokuhair2 = bitmap_gokuhair2;
        op_hairblonde = bitmap_hairblonde;
        op_longhair = bitmap_longhair;
        op_moustache_glasses = bitmap_moustache_glasses;
        op_beard3 = bitmap_beard3;
    }
    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        if(FaceFilterActivity.imageid !=FaceFilterActivity.tempImageId)FaceFilterActivity.tempImageId = FaceFilterActivity.imageid;
        switch(FaceFilterActivity.tempImageId){
            case (R.drawable.hair):{
                System.out.println(FaceFilterActivity.tempImageId);
                op_head = Bitmap.createScaledBitmap(bitmap_head, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_head.getHeight() * face.getWidth()) / bitmap_head.getWidth())), false);
                break;
            }
            case (R.drawable.glasses):{
                System.out.println(FaceFilterActivity.tempImageId);
                op_glasses =  Bitmap.createScaledBitmap(bitmap_glasses, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_glasses.getHeight() * face.getWidth()) / bitmap_glasses.getWidth())), false);
                break;
            }
            case (R.drawable.cap):{
                op_cap =  Bitmap.createScaledBitmap(bitmap_cap, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_cap.getHeight() * face.getWidth()) / bitmap_cap.getWidth())), false);
                break;
            }
            case(R.drawable.beard):{
                op_beard =  Bitmap.createScaledBitmap(bitmap_beard, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_beard.getHeight() * face.getWidth()) / bitmap_beard.getWidth())), false);
                break;
            }
            case(R.drawable.beard_one):{
                op_beard1 =  Bitmap.createScaledBitmap(bitmap_beard1, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_beard1.getHeight() * face.getWidth()) / bitmap_beard1.getWidth())), false);
                break;
            }
            case(R.drawable.goku_hair):{
                op_gokuhair = Bitmap.createScaledBitmap(bitmap_gokuhair, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_gokuhair.getHeight() * face.getWidth()) / bitmap_gokuhair.getWidth())), false);
                break;
            }
            case(R.drawable.blue_goggles):{
                op_bluegoggles = Bitmap.createScaledBitmap(bitmap_bluegoggles, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_bluegoggles.getHeight() * face.getWidth()) / bitmap_bluegoggles.getWidth())), false);
                break;
            }
            case (R.drawable.goku):{
                op_gokuhair2 = Bitmap.createScaledBitmap(bitmap_gokuhair2, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_gokuhair2.getHeight() * face.getWidth()) / bitmap_gokuhair2.getWidth())), false);
                break;
            }
            case(R.drawable.hair_blonde):{
                op_hairblonde = Bitmap.createScaledBitmap(bitmap_hairblonde, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_hairblonde.getHeight() * face.getWidth()) / bitmap_hairblonde.getWidth())), false);
                break;
            }
            case(R.drawable.long_hair):{
                op_longhair = Bitmap.createScaledBitmap(bitmap_longhair, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_longhair.getHeight() * face.getWidth()) / bitmap_longhair.getWidth())), false);
                break;
            }
            case(R.drawable.moustache_glasses):{
                op_moustache_glasses = Bitmap.createScaledBitmap(bitmap_moustache_glasses, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_moustache_glasses.getHeight() * face.getWidth()) / bitmap_moustache_glasses.getWidth())), false);
                break;
            }
            case(R.drawable.beard_three):{
                op_beard3 = Bitmap.createScaledBitmap(bitmap_beard3, (int) scaleX(face.getWidth()),
                        (int) scaleY(((bitmap_beard3.getHeight() * face.getWidth()) / bitmap_beard3.getWidth())), false);
                break;
            }
        }
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }
        //if(FaceFilterActivity.imageid !=FaceFilterActivity.tempImageId)FaceFilterActivity.tempImageId = FaceFilterActivity.imageid;
        switch(FaceFilterActivity.tempImageId){
            case (R.drawable.hair):{
               drawhair1(canvas,face);
                break;
            }
            case (R.drawable.glasses):{
                drawGlasses(canvas,face);
                break;
            }
            case (R.drawable.cap):{
               drawCrown(canvas,face);
                break;
            }
            case(R.drawable.beard):{
                drawBeard(canvas,face);
                break;
            }
            case(R.drawable.beard_one):{
                drawBeard1(canvas,face);
                break;
            }
            case(R.drawable.goku_hair):{
               drawGoku(canvas,face);
                break;
            }
            case(R.drawable.blue_goggles):{
                drawBlueGoggles(canvas,face);
                break;
            }
            case (R.drawable.goku):{
                drawGoku1(canvas,face);
                break;
            }
            case(R.drawable.hair_blonde):{
               drawhairBlonde(canvas,face);
                break;
            }
            case(R.drawable.long_hair):{
                drawLonghair(canvas,face);
                break;
            }
            case(R.drawable.moustache_glasses):{
                drawMoustacheGlasses(canvas,face);
                break;
            }
            case(R.drawable.beard_three):{
                drawBeard3(canvas,face);
                break;
            }
        }
    }

    private void drawMoustacheGlasses(Canvas canvas, Face face) {
        List<Landmark> landmarks = face.getLandmarks();
        float bottommouthX=0,bottommouthY=0;
        for(Landmark l: landmarks){
            int cx = (int) (translateX(l.getPosition().x ));
            int cy = (int) (translateY(l.getPosition().y ));
            if(l.getType() == Landmark.NOSE_BASE ){
                bottommouthX = cx;
                bottommouthY = cy;
            }
        }
        if((bottommouthX!=0 && bottommouthY!=0)) {;
            canvas.drawCircle(bottommouthX,bottommouthY,10,mFacePositionPaint);
            float xOffset = scaleX(face.getWidth() / 2f);
            float yOffset = scaleY(face.getHeight() / 2f);
            float left = bottommouthX - xOffset;
            float top = bottommouthY - yOffset;
            float right = bottommouthX + xOffset;
            float bottom = bottommouthY + yOffset;
            Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
            canvas.drawRect(rect,mBoxPaint);
            canvas.drawBitmap(op_moustache_glasses,null,rect,new Paint());

        }
    }
    private void drawhair1(Canvas canvas,Face face){
        float x = translateX(face.getPosition().x + face.getWidth() / 2f);
        float y =translateY(face.getPosition().y + face.getHeight() / 3.5f );
        float xOffset = scaleX(face.getWidth() / 1.9f);
        float yOffset = scaleY(face.getHeight() / 3f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left,top,right,bottom,mBoxPaint);
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        canvas.drawBitmap(op_head, null, rect, new Paint());
    }
    private void drawLonghair(Canvas canvas, Face face) {
        float x = translateX(face.getPosition().x + face.getWidth() / 2f);
        float y =translateY(face.getPosition().y + face.getHeight() / 2f );
        float xOffset = scaleX(face.getWidth() / 1.2f);
        float yOffset = scaleY(face.getHeight() / 1.5f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + 2*yOffset;
        canvas.drawRect(left,top,right,bottom,mBoxPaint);
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        canvas.drawBitmap(op_longhair, null, rect, new Paint());
    }
    private void drawhairBlonde(Canvas canvas, Face face) {
        float x = translateX(face.getPosition().x + face.getWidth() / 2f);
        float y =translateY(face.getPosition().y + face.getHeight() / 3.5f );
        float xOffset = scaleX(face.getWidth() / 2.3f);
        float yOffset = scaleY(face.getHeight() / 2f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left,top,right,bottom,mBoxPaint);
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        canvas.drawBitmap(op_hairblonde, null, rect, new Paint());
    }
    private void drawBeard(Canvas canvas,Face face){
        List<Landmark> landmarks = face.getLandmarks();
        float bottommouthX=0,bottommouthY=0;
        for(Landmark l: landmarks){
            int cx = (int) (translateX(l.getPosition().x ));
            int cy = (int) (translateY(l.getPosition().y ));
            if(l.getType() == Landmark.NOSE_BASE ){
                bottommouthX = cx;
                bottommouthY = cy;
            }
        }
        if((bottommouthX!=0 && bottommouthY!=0)) {
//            int midx = (mouthleftX+mouthrightX)/2;
//            int midy = (mouthrightY+mouthleftY)/2;
//            canvas.drawCircle(midx,midy,10,mFacePositionPaint);
//            canvas.drawCircle(mouthleftX,mouthleftY,10,mFacePositionPaint);
//            canvas.drawCircle(mouthrightX,mouthrightY,10,mFacePositionPaint);
            bottommouthY = bottommouthY + (face.getHeight()/2f);
            canvas.drawCircle(bottommouthX,bottommouthY,10,mFacePositionPaint);
            float xOffset = scaleX(face.getWidth() / 2f);
            float yOffset = scaleY(face.getHeight() / 2.5f);
            float left = bottommouthX - xOffset;
            float top = bottommouthY - yOffset;
            float right = bottommouthX + xOffset;
            float bottom = bottommouthY + yOffset;
            Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
            //canvas.drawRect(rect,mBoxPaint);
            canvas.drawBitmap(op_beard,null,rect,new Paint());

        }
    }
    private void drawBeard1(Canvas canvas, Face face) {
        List<Landmark> landmarks = face.getLandmarks();
        float bottommouthX = 0, bottommouthY = 0;
        for (Landmark l : landmarks) {
            int cx = (int) (translateX(l.getPosition().x));
            int cy = (int) (translateY(l.getPosition().y));
            //drawOnImageView(canvas,l.getType(),cx,cy);
            if (l.getType() == Landmark.NOSE_BASE) {
                bottommouthX = cx;
                bottommouthY = cy;
            }
        }
        if ((bottommouthX != 0 && bottommouthY != 0)) {
//            int midx = (mouthleftX+mouthrightX)/2;
//            int midy = (mouthrightY+mouthleftY)/2;
//            canvas.drawCircle(midx,midy,10,mFacePositionPaint);
//            canvas.drawCircle(mouthleftX,mouthleftY,10,mFacePositionPaint);
//            canvas.drawCircle(mouthrightX,mouthrightY,10,mFacePositionPaint);
            bottommouthY = bottommouthY + (face.getHeight() / 1.5f);
            canvas.drawCircle(bottommouthX, bottommouthY, 10, mFacePositionPaint);
            float xOffset = scaleX(face.getWidth() / 1.7f);
            float yOffset = scaleY(face.getHeight() / 2f);
            float left = bottommouthX - xOffset;
            float top = bottommouthY - yOffset;
            float right = bottommouthX + xOffset;
            float bottom = bottommouthY + yOffset;
            Rect rect = new Rect((int) left, (int) top, (int) right, (int) bottom);
            //canvas.drawRect(rect,mBoxPaint);
            canvas.drawBitmap(op_beard1, null, rect, new Paint());
        }
    }
    private void drawBeard3(Canvas canvas, Face face) {
        List<Landmark> landmarks = face.getLandmarks();
        float bottommouthX = 0, bottommouthY = 0;
        for (Landmark l : landmarks) {
            int cx = (int) (translateX(l.getPosition().x));
            int cy = (int) (translateY(l.getPosition().y));
            //drawOnImageView(canvas,l.getType(),cx,cy);
            if (l.getType() == Landmark.NOSE_BASE) {
                bottommouthX = cx;
                bottommouthY = cy;
            }
        }
        if ((bottommouthX != 0 && bottommouthY != 0)) {
//            int midx = (mouthleftX+mouthrightX)/2;
//            int midy = (mouthrightY+mouthleftY)/2;
//            canvas.drawCircle(midx,midy,10,mFacePositionPaint);
//            canvas.drawCircle(mouthleftX,mouthleftY,10,mFacePositionPaint);
//            canvas.drawCircle(mouthrightX,mouthrightY,10,mFacePositionPaint);
            bottommouthY = bottommouthY + (face.getHeight() / 2f);
            canvas.drawCircle(bottommouthX, bottommouthY, 10, mFacePositionPaint);
            float xOffset = scaleX(face.getWidth() / 2.5f);
            float yOffset = scaleY(face.getHeight() / 3f);
            float left = bottommouthX - xOffset;
            float top = bottommouthY - yOffset;
            float right = bottommouthX + xOffset;
            float bottom = bottommouthY + yOffset;
            Rect rect = new Rect((int) left, (int) top, (int) right, (int) bottom);
            //canvas.drawRect(rect,mBoxPaint);
            canvas.drawBitmap(op_beard3, null, rect, new Paint());
        }
    }
    private void drawGoku(Canvas canvas,Face face){
        float x = translateX(face.getPosition().x + face.getWidth() / 2f);
        float y =translateY(face.getPosition().y - face.getHeight() / 20f);
        float xOffset = scaleX(face.getWidth() / 1.15f);
        float yOffset = scaleY(face.getHeight() / 1.55f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left,top,right,bottom,mBoxPaint);
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        canvas.drawBitmap(op_gokuhair,null, rect, new Paint());
    }
    private void drawGoku1(Canvas canvas, Face face) {
        float x = translateX(face.getPosition().x + face.getWidth() / 2f);
        float y =translateY(face.getPosition().y - face.getHeight()/10f);
        float xOffset = scaleX(face.getWidth() / 1.15f);
        float yOffset = scaleY(face.getHeight() / 1.5f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left,top,right,bottom,mBoxPaint);
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        canvas.drawBitmap(op_gokuhair2,null, rect, new Paint());
    }
    private void drawCrown(Canvas canvas ,Face face){
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y =translateY(face.getPosition().y + face.getHeight() / 5f );
        // canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);
        float xOffset = scaleX(face.getWidth() / 2f);
        float yOffset = scaleY(face.getHeight() / 2.3f);
        //float xOffset = scaleX(face.getWidth()/2.1f);
        //float yOffset = scaleY(face.getHeight() / 2.0f);
        // canvas.drawText("id: " + mFaceId, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        //canvas.drawRect(left,top,right,bottom,mBoxPaint);
        Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
        canvas.drawBitmap(op_cap, null, rect, new Paint());
    }
    private void drawGlasses(Canvas canvas,Face face){
        List<Landmark> landmarks = face.getLandmarks();
        int lefteyeposX=0,lefteyeposY=0,righteyeposX=0,righteyeposY=0,mouthleftX=0,mouthleftY=0;
        for(Landmark l: landmarks){
            int cx = (int) (translateX(l.getPosition().x ));
            int cy = (int) (translateY(l.getPosition().y ));
            //drawOnImageView(canvas,l.getType(),cx,cy);
            if(l.getType() == Landmark.LEFT_EYE ){
                lefteyeposX = cx;
                lefteyeposY = cy;
            }
            else if(l.getType() == Landmark.RIGHT_EYE) {
                righteyeposX = cx;
                righteyeposY = cy;
            }
        }
        if(lefteyeposX!=0 && lefteyeposY!=0 && righteyeposX!=0 && righteyeposY!=0) {
            int midx = (lefteyeposX+righteyeposX)/2;
            int midy = (lefteyeposY+righteyeposY)/2;
            //canvas.drawCircle(midx,midy,10,mFacePositionPaint);
            //canvas.drawCircle(lefteyeposX,lefteyeposY,10,mFacePositionPaint);
            //canvas.drawCircle(righteyeposX,righteyeposY,10,mFacePositionPaint);
            float xOffset = scaleX(face.getWidth() / 2.3f);
            float yOffset = scaleY(face.getHeight() / 5.3f);
            float left = midx - xOffset;
            float top = midy - yOffset;
            float right = midx + xOffset;
            float bottom = midy + yOffset;
            double angle = ((float)lefteyeposY-(float)righteyeposY)/((float)lefteyeposX-(float)righteyeposX);
            Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
            canvas.drawBitmap(op_glasses,null ,rect , new Paint());
        }
    }
    private void drawBlueGoggles(Canvas canvas, Face face) {
        List<Landmark> landmarks = face.getLandmarks();
        int lefteyeposX=0,lefteyeposY=0,righteyeposX=0,righteyeposY=0;
        for(Landmark l: landmarks){
            int cx = (int) (translateX(l.getPosition().x ));
            int cy = (int) (translateY(l.getPosition().y ));
            if(l.getType() == Landmark.LEFT_EYE ){
                lefteyeposX = cx;
                lefteyeposY = cy;
            }
            else if(l.getType() == Landmark.RIGHT_EYE) {
                righteyeposX = cx;
                righteyeposY = cy;
            }
        }
        if(lefteyeposX!=0 && lefteyeposY!=0 && righteyeposX!=0 && righteyeposY!=0) {
            int midx = (lefteyeposX+righteyeposX)/2;
            int midy = (lefteyeposY+righteyeposY)/2;
            //canvas.drawCircle(midx,midy,10,mFacePositionPaint);
            //canvas.drawCircle(lefteyeposX,lefteyeposY,10,mFacePositionPaint);
            //canvas.drawCircle(righteyeposX,righteyeposY,10,mFacePositionPaint);
            float xOffset = scaleX(face.getWidth() / 2f);
            float yOffset = scaleY(face.getHeight() / 4.5f);
            float left = midx - xOffset;
            float top = midy - yOffset;
            float right = midx + xOffset;
            float bottom = midy + yOffset;
            Rect rect = new Rect((int)left,(int)top,(int)right,(int)bottom);
            canvas.drawBitmap(op_bluegoggles,null ,rect , new Paint());
        }
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle,int x,int y)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle,source.getWidth()/2,source.getHeight()/2);
        return Bitmap.createBitmap(source, 0, 0,source.getWidth() , source.getHeight(), matrix, true);
    }

    private float getNoseAndMouthDistance(PointF nose, PointF mouth) {
        return (float) Math.hypot(mouth.x - nose.x, mouth.y - nose.y);
    }
}
