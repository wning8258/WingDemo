/*******************************************************************************
 * Copyright 2011-2014 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nostra13.universalimageloader.core.display;

import android.graphics.*;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 上面是圆角，底下是方角的显示效果
 * 
 * <br />
 * This implementation is inspired by
 * <a href="http://www.curious-creature.org/2012/12/11/android-recipe-1-image-with-rounded-corners/">
 * Romain Guy's article</a>. It rounds images using custom drawable drawing. Original bitmap isn't changed.
 * <br />
 * <br />
 * If this implementation doesn't meet your needs then consider
 * <a href="https://github.com/vinc3m1/RoundedImageView">RoundedImageView</a> or
 * <a href="https://github.com/Pkmmte/CircularImageView">CircularImageView</a> projects for usage.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.5.6
 */
public class TopCornerBitmapDisplayer implements BitmapDisplayer {

	protected final int cornerRadius;
	protected final int margin = 0;

	public TopCornerBitmapDisplayer(int cornerRadiusPixels) {
		this.cornerRadius = cornerRadiusPixels;
	}

	@Override
	public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
		if (!(imageAware instanceof ImageViewAware)) {
			throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
		}

		imageAware.setImageDrawable(new RoundedDrawable(bitmap, cornerRadius, margin));
	}

	public static class RoundedDrawable extends Drawable {

		protected final float cornerRadius;
		protected final int margin;

		protected final RectF mRectT = new RectF();//顶部区域
		protected final RectF mBitmapRectT;
		protected final BitmapShader bitmapShaderT;

		protected final RectF mRectB = new RectF();//底部区域
		protected final RectF mBitmapRectB;
		protected final BitmapShader bitmapShaderB;
		
		protected final Paint paintT;
		protected final Paint paintB;
		
		private Bitmap mBitmap;
		
		public Bitmap getBitmap(){
			return mBitmap;
		}

		public RoundedDrawable(Bitmap bitmap, int cornerRadius, int margin) {
			this.cornerRadius = cornerRadius;
			this.margin = margin;
			
			mBitmap=bitmap;

			bitmapShaderT = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mBitmapRectT = new RectF(margin, margin, bitmap.getWidth() - margin, margin + cornerRadius * 2);

			bitmapShaderB = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mBitmapRectB = new RectF(margin, margin + cornerRadius, bitmap.getWidth() - margin, bitmap.getHeight() - margin);
			
			paintT = new Paint();
			paintT.setAntiAlias(true);
			paintT.setShader(bitmapShaderT);
			
			paintB = new Paint();
			paintB.setAntiAlias(true);
			paintB.setShader(bitmapShaderB);
		}

		@Override
		protected void onBoundsChange(Rect bounds) {
			super.onBoundsChange(bounds);
			float ratio = bounds.height() / mBitmapRectT.width();
			mRectT.set(margin, margin, bounds.width() - margin, margin + cornerRadius * 2 * ratio);
			mRectB.set(margin, margin + cornerRadius * ratio, bounds.width() - margin, bounds.height() - margin);

			// Resize the original bitmap to fit the new bound
			Matrix shaderMatrix = new Matrix();
			shaderMatrix.setRectToRect(mBitmapRectT, mRectT, Matrix.ScaleToFit.FILL);
			bitmapShaderT.setLocalMatrix(shaderMatrix);

			Matrix shaderMatrix2 = new Matrix();
			shaderMatrix2.setRectToRect(mBitmapRectB, mRectB, Matrix.ScaleToFit.FILL);
			bitmapShaderB.setLocalMatrix(shaderMatrix2);

		}

		@Override
		public void draw(Canvas canvas) {
			canvas.drawRoundRect(mRectT, cornerRadius, cornerRadius, paintT);
			canvas.drawRect(mRectB, paintB);
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		@Override
		public void setAlpha(int alpha) {
			paintT.setAlpha(alpha);
			paintB.setAlpha(alpha);
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			paintT.setColorFilter(cf);
			paintB.setColorFilter(cf);
		}
	}
}
