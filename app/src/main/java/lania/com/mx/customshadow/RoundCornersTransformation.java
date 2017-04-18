package lania.com.mx.customshadow;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @author Clemente Morales Fernandez
 * @since 4/11/2017.
 */

public class RoundCornersTransformation extends BitmapTransformation {
    private final float mDiameter;
    private float mRadius;
    private float mMargin;
    private BitmapPool mBitmapPool;
    private CornerType mCornerType;

    public enum CornerType {
        ALL, TOP, BOTTOM
    }

    public RoundCornersTransformation(BitmapPool pool, float radius, float margin) {
        this(pool, radius, margin, CornerType.ALL);
    }

    public RoundCornersTransformation(BitmapPool pool, float radius, float margin,
                                      CornerType cornerType) {
        super(pool);
        mBitmapPool = pool;
        mRadius = radius;
        mDiameter = mRadius * 2;
        mMargin = margin;
        mCornerType = cornerType;
    }

    @Override
    public Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {

        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawRoundRect(canvas, paint, width, height);
        return BitmapResource.obtain(bitmap, mBitmapPool).get();
    }

    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        float right = width - mMargin;
        float bottom = height - mMargin;

        switch (mCornerType) {
            case ALL:
                canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
                break;
            case TOP:
                drawTopRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM:
                drawBottomRoundRect(canvas, paint, right, bottom);
                break;
            default:
                canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
                break;
        }
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right, bottom), paint);
    }

    private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
                paint);
        canvas.drawRect(new RectF(mMargin, mMargin, right, bottom - mRadius), paint);
    }

    @Override
    public String getId() {
        return "RoundedTransformation(radius=" + mRadius + ", margin=" + mMargin + ")";
    }
}

