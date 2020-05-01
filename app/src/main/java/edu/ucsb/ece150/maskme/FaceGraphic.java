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

package edu.ucsb.ece150.maskme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import edu.ucsb.ece150.maskme.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
public class FaceGraphic extends GraphicOverlay.Graphic {


    private volatile Face mFace;
    private Context context;
    private FaceTrackerActivity.MaskType maskType = FaceTrackerActivity.MaskType.FIRST;

    FaceGraphic(GraphicOverlay overlay, Context context) {
        super(overlay);
        this.context = context;

    }

    void setMaskType(FaceTrackerActivity.MaskType maskType) {
        this.maskType = maskType;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
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

        // [TODO] Draw real time masks for a single face

        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);

        float deltaX, deltaY;
        Bitmap mask;

        if (maskType == FaceTrackerActivity.MaskType.SECOND) {
            deltaX = 1.3f * scaleX(mFace.getWidth()/2);
            deltaY = 1.3f * scaleY(mFace.getHeight()/2);
            mask = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat);
        } else {
            deltaX = 1f * scaleX(mFace.getWidth()/2);
            deltaY = 1.3f * scaleY(mFace.getHeight()/2);
            mask = BitmapFactory.decodeResource(context.getResources(), R.drawable.bear);
        }


        int left = (int) (x - deltaX);
        int top= (int) (y - deltaY);
        int right = (int) (x + deltaX);
        int bottom = (int) (y + deltaY);

        Rect destBounds = new Rect(left, top, right, bottom);
        canvas.drawBitmap(mask, null, destBounds, null);




    }
}
