package edu.ucsb.ece150.maskme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;

import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.gms.vision.face.Face;
import edu.ucsb.ece150.maskme.FaceTrackerActivity.MaskType;

public class MaskedImageView extends AppCompatImageView {

    private SparseArray<Face> faces = null;
    private MaskType maskType = MaskType.FIRST;
    Paint mPaint = new Paint();
    private Bitmap mBitmap;

    public MaskedImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        if(mBitmap == null){
            return;
        }
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        drawBitmap(canvas, scale);

        switch (maskType){
            case FIRST:
                drawFirstMaskOnCanvas(canvas, scale);
                break;
            case SECOND:
                drawSecondMaskOnCanvas(canvas, scale);
                break;
        }
    }

    protected void drawMask(SparseArray<Face> faces, MaskType maskType){
        this.faces = faces;
        this.maskType = maskType;
        this.invalidate();
    }

    private void drawBitmap(Canvas canvas, double scale) {
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();

        Rect destBounds = new Rect(0, 0, (int)(imageWidth * scale), (int)(imageHeight * scale));
        canvas.drawBitmap(mBitmap, null, destBounds, null);
    }

    private void drawFirstMaskOnCanvas(Canvas canvas, double scale) {

        // [TODO] Draw first type of mask on the static photo
        // 1. set properties of mPaint
        // 2. get positions of faces and draw masks on faces.

    }

    private void drawSecondMaskOnCanvas( Canvas canvas, double scale ) {
        // [TODO] Draw second type of mask on the static photo
        // 1. set properties of mPaint
        // 2. get positions of faces and draw masks on faces.

    }

    public void noFaces() {
        faces = null;
    }

    public void reset() {
        faces = null;
        setImageBitmap(null);
    }
}
