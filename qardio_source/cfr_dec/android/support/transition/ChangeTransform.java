/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TypeEvaluator
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Matrix
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Property
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  org.xmlpull.v1.XmlPullParser
 */
package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.support.transition.AnimatorUtils;
import android.support.transition.FloatArrayEvaluator;
import android.support.transition.GhostViewImpl;
import android.support.transition.GhostViewUtils;
import android.support.transition.MatrixUtils;
import android.support.transition.PathMotion;
import android.support.transition.PropertyValuesHolderUtils;
import android.support.transition.R;
import android.support.transition.Styleable;
import android.support.transition.Transition;
import android.support.transition.TransitionListenerAdapter;
import android.support.transition.TransitionSet;
import android.support.transition.TransitionValues;
import android.support.transition.ViewUtils;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform
extends Transition {
    private static final Property<PathAnimatorMatrix, float[]> NON_TRANSLATIONS_PROPERTY;
    private static final boolean SUPPORTS_VIEW_REMOVAL_SUPPRESSION;
    private static final Property<PathAnimatorMatrix, PointF> TRANSLATIONS_PROPERTY;
    private static final String[] sTransitionProperties;
    private boolean mReparent = true;
    private Matrix mTempMatrix = new Matrix();
    private boolean mUseOverlay = true;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = true;
        sTransitionProperties = new String[]{"android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix"};
        NON_TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, float[]>(float[].class, "nonTranslations"){

            public float[] get(PathAnimatorMatrix pathAnimatorMatrix) {
                return null;
            }

            public void set(PathAnimatorMatrix pathAnimatorMatrix, float[] arrf) {
                pathAnimatorMatrix.setValues(arrf);
            }
        };
        TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, PointF>(PointF.class, "translations"){

            public PointF get(PathAnimatorMatrix pathAnimatorMatrix) {
                return null;
            }

            public void set(PathAnimatorMatrix pathAnimatorMatrix, PointF pointF) {
                pathAnimatorMatrix.setTranslation(pointF);
            }
        };
        if (Build.VERSION.SDK_INT < 21) {
            bl = false;
        }
        SUPPORTS_VIEW_REMOVAL_SUPPRESSION = bl;
    }

    public ChangeTransform() {
    }

    public ChangeTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context = context.obtainStyledAttributes(attributeSet, Styleable.CHANGE_TRANSFORM);
        this.mUseOverlay = TypedArrayUtils.getNamedBoolean((TypedArray)context, (XmlPullParser)attributeSet, "reparentWithOverlay", 1, true);
        this.mReparent = TypedArrayUtils.getNamedBoolean((TypedArray)context, (XmlPullParser)attributeSet, "reparent", 0, true);
        context.recycle();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void captureValues(TransitionValues transitionValues) {
        Transforms transforms;
        View view;
        block3: {
            block2: {
                view = transitionValues.view;
                if (view.getVisibility() == 8) break block2;
                transitionValues.values.put("android:changeTransform:parent", (Object)view.getParent());
                transforms = new Transforms(view);
                transitionValues.values.put("android:changeTransform:transforms", transforms);
                transforms = view.getMatrix();
                transforms = transforms == null || transforms.isIdentity() ? null : new Matrix((Matrix)transforms);
                transitionValues.values.put("android:changeTransform:matrix", transforms);
                if (this.mReparent) break block3;
            }
            return;
        }
        transforms = new Matrix();
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        ViewUtils.transformMatrixToGlobal((View)viewGroup, (Matrix)transforms);
        transforms.preTranslate((float)(-viewGroup.getScrollX()), (float)(-viewGroup.getScrollY()));
        transitionValues.values.put("android:changeTransform:parentMatrix", transforms);
        transitionValues.values.put("android:changeTransform:intermediateMatrix", view.getTag(R.id.transition_transform));
        transitionValues.values.put("android:changeTransform:intermediateParentMatrix", view.getTag(R.id.parent_matrix));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private void createGhostView(ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        void var3_6;
        void var2_5;
        View view;
        block6: {
            block5: {
                void var1_3;
                view = var3_6.view;
                Object object2 = new Matrix((Matrix)var3_6.values.get("android:changeTransform:parentMatrix"));
                ViewUtils.transformMatrixToLocal((View)object, (Matrix)object2);
                object2 = GhostViewUtils.addGhost(view, object, (Matrix)object2);
                if (object2 == null) break block5;
                object2.reserveEndViewTransition((ViewGroup)var2_5.values.get("android:changeTransform:parent"), var2_5.view);
                ChangeTransform changeTransform = this;
                while (var1_3.mParent != null) {
                    TransitionSet transitionSet = var1_3.mParent;
                }
                var1_3.addListener(new GhostListener(view, (GhostViewImpl)object2));
                if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) break block6;
            }
            return;
        }
        if (var2_5.view != var3_6.view) {
            ViewUtils.setTransitionAlpha(var2_5.view, 0.0f);
        }
        ViewUtils.setTransitionAlpha(view, 1.0f);
    }

    private ObjectAnimator createTransformAnimator(TransitionValues transitionValues, TransitionValues transitionValues2, final boolean bl) {
        transitionValues = (Matrix)transitionValues.values.get("android:changeTransform:matrix");
        Object object = (Matrix)transitionValues2.values.get("android:changeTransform:matrix");
        Object object2 = transitionValues;
        if (transitionValues == null) {
            object2 = MatrixUtils.IDENTITY_MATRIX;
        }
        transitionValues = object;
        if (object == null) {
            transitionValues = MatrixUtils.IDENTITY_MATRIX;
        }
        if (object2.equals((Object)transitionValues)) {
            return null;
        }
        object = (Transforms)transitionValues2.values.get("android:changeTransform:transforms");
        transitionValues2 = transitionValues2.view;
        ChangeTransform.setIdentityTransforms((View)transitionValues2);
        Path path = new float[9];
        object2.getValues((float[])path);
        float[] arrf = new float[9];
        transitionValues.getValues(arrf);
        object2 = new PathAnimatorMatrix((View)transitionValues2, (float[])path);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofObject(NON_TRANSLATIONS_PROPERTY, (TypeEvaluator)new FloatArrayEvaluator(new float[9]), (Object[])new float[][]{path, arrf});
        path = this.getPathMotion().getPath(path[2], path[5], arrf[2], arrf[5]);
        path = ObjectAnimator.ofPropertyValuesHolder((Object)object2, (PropertyValuesHolder[])new PropertyValuesHolder[]{propertyValuesHolder, PropertyValuesHolderUtils.ofPointF(TRANSLATIONS_PROPERTY, path)});
        transitionValues = new AnimatorListenerAdapter((Matrix)transitionValues, (View)transitionValues2, (Transforms)object, (PathAnimatorMatrix)object2){
            private boolean mIsCanceled;
            private Matrix mTempMatrix = new Matrix();
            final /* synthetic */ Matrix val$finalEndMatrix;
            final /* synthetic */ PathAnimatorMatrix val$pathAnimatorMatrix;
            final /* synthetic */ Transforms val$transforms;
            final /* synthetic */ View val$view;
            {
                this.val$finalEndMatrix = matrix;
                this.val$view = view;
                this.val$transforms = transforms;
                this.val$pathAnimatorMatrix = pathAnimatorMatrix;
            }

            private void setCurrentMatrix(Matrix matrix) {
                this.mTempMatrix.set(matrix);
                this.val$view.setTag(R.id.transition_transform, (Object)this.mTempMatrix);
                this.val$transforms.restore(this.val$view);
            }

            public void onAnimationCancel(Animator animator2) {
                this.mIsCanceled = true;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onAnimationEnd(Animator animator2) {
                if (!this.mIsCanceled) {
                    if (bl && ChangeTransform.this.mUseOverlay) {
                        this.setCurrentMatrix(this.val$finalEndMatrix);
                    } else {
                        this.val$view.setTag(R.id.transition_transform, null);
                        this.val$view.setTag(R.id.parent_matrix, null);
                    }
                }
                ViewUtils.setAnimationMatrix(this.val$view, null);
                this.val$transforms.restore(this.val$view);
            }

            public void onAnimationPause(Animator animator2) {
                this.setCurrentMatrix(this.val$pathAnimatorMatrix.getMatrix());
            }

            public void onAnimationResume(Animator animator2) {
                ChangeTransform.setIdentityTransforms(this.val$view);
            }
        };
        path.addListener((Animator.AnimatorListener)transitionValues);
        AnimatorUtils.addPauseListener((Animator)path, (AnimatorListenerAdapter)transitionValues);
        return path;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean parentsMatch(ViewGroup object, ViewGroup viewGroup) {
        void var2_3;
        boolean bl = false;
        if (!this.isValidTarget((View)object) || !this.isValidTarget((View)var2_3)) {
            if (object != var2_3) return false;
            return true;
        }
        TransitionValues transitionValues = this.getMatchedTransitionValues((View)object, true);
        if (transitionValues == null) return bl;
        if (var2_3 != transitionValues.view) return false;
        return true;
    }

    private static void setIdentityTransforms(View view) {
        ChangeTransform.setTransforms(view, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    private void setMatricesForParent(TransitionValues transitionValues, TransitionValues transitionValues2) {
        Matrix matrix = (Matrix)transitionValues2.values.get("android:changeTransform:parentMatrix");
        transitionValues2.view.setTag(R.id.parent_matrix, (Object)matrix);
        Matrix matrix2 = this.mTempMatrix;
        matrix2.reset();
        matrix.invert(matrix2);
        matrix = (Matrix)transitionValues.values.get("android:changeTransform:matrix");
        transitionValues2 = matrix;
        if (matrix == null) {
            transitionValues2 = new Matrix();
            transitionValues.values.put("android:changeTransform:matrix", transitionValues2);
        }
        transitionValues2.postConcat((Matrix)transitionValues.values.get("android:changeTransform:parentMatrix"));
        transitionValues2.postConcat(matrix2);
    }

    private static void setTransforms(View view, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        ViewCompat.setTranslationZ(view, f3);
        view.setScaleX(f4);
        view.setScaleY(f5);
        view.setRotationX(f6);
        view.setRotationY(f7);
        view.setRotation(f8);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
        if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
            ((ViewGroup)transitionValues.view.getParent()).startViewTransition(transitionValues.view);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) return null;
        if (transitionValues2 == null) return null;
        if (!transitionValues.values.containsKey("android:changeTransform:parent")) return null;
        if (!transitionValues2.values.containsKey("android:changeTransform:parent")) {
            return null;
        }
        ViewGroup viewGroup2 = (ViewGroup)transitionValues.values.get("android:changeTransform:parent");
        ViewGroup viewGroup3 = (ViewGroup)transitionValues2.values.get("android:changeTransform:parent");
        boolean bl = this.mReparent && !this.parentsMatch(viewGroup2, viewGroup3);
        viewGroup3 = (Matrix)transitionValues.values.get("android:changeTransform:intermediateMatrix");
        if (viewGroup3 != null) {
            transitionValues.values.put("android:changeTransform:matrix", (Object)viewGroup3);
        }
        if ((viewGroup3 = (Matrix)transitionValues.values.get("android:changeTransform:intermediateParentMatrix")) != null) {
            transitionValues.values.put("android:changeTransform:parentMatrix", (Object)viewGroup3);
        }
        if (bl) {
            this.setMatricesForParent(transitionValues, transitionValues2);
        }
        viewGroup3 = this.createTransformAnimator(transitionValues, transitionValues2, bl);
        if (bl && viewGroup3 != null && this.mUseOverlay) {
            this.createGhostView(viewGroup, transitionValues, transitionValues2);
            return viewGroup3;
        }
        viewGroup = viewGroup3;
        if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) return viewGroup;
        viewGroup2.endViewTransition(transitionValues.view);
        return viewGroup3;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private static class GhostListener
    extends TransitionListenerAdapter {
        private GhostViewImpl mGhostView;
        private View mView;

        GhostListener(View view, GhostViewImpl ghostViewImpl) {
            this.mView = view;
            this.mGhostView = ghostViewImpl;
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            transition.removeListener(this);
            GhostViewUtils.removeGhost(this.mView);
            this.mView.setTag(R.id.transition_transform, null);
            this.mView.setTag(R.id.parent_matrix, null);
        }

        @Override
        public void onTransitionPause(Transition transition) {
            this.mGhostView.setVisibility(4);
        }

        @Override
        public void onTransitionResume(Transition transition) {
            this.mGhostView.setVisibility(0);
        }
    }

    private static class PathAnimatorMatrix {
        private final Matrix mMatrix = new Matrix();
        private float mTranslationX;
        private float mTranslationY;
        private final float[] mValues;
        private final View mView;

        PathAnimatorMatrix(View view, float[] arrf) {
            this.mView = view;
            this.mValues = (float[])arrf.clone();
            this.mTranslationX = this.mValues[2];
            this.mTranslationY = this.mValues[5];
            this.setAnimationMatrix();
        }

        private void setAnimationMatrix() {
            this.mValues[2] = this.mTranslationX;
            this.mValues[5] = this.mTranslationY;
            this.mMatrix.setValues(this.mValues);
            ViewUtils.setAnimationMatrix(this.mView, this.mMatrix);
        }

        Matrix getMatrix() {
            return this.mMatrix;
        }

        void setTranslation(PointF pointF) {
            this.mTranslationX = pointF.x;
            this.mTranslationY = pointF.y;
            this.setAnimationMatrix();
        }

        void setValues(float[] arrf) {
            System.arraycopy(arrf, 0, this.mValues, 0, arrf.length);
            this.setAnimationMatrix();
        }
    }

    private static class Transforms {
        final float mRotationX;
        final float mRotationY;
        final float mRotationZ;
        final float mScaleX;
        final float mScaleY;
        final float mTranslationX;
        final float mTranslationY;
        final float mTranslationZ;

        Transforms(View view) {
            this.mTranslationX = view.getTranslationX();
            this.mTranslationY = view.getTranslationY();
            this.mTranslationZ = ViewCompat.getTranslationZ(view);
            this.mScaleX = view.getScaleX();
            this.mScaleY = view.getScaleY();
            this.mRotationX = view.getRotationX();
            this.mRotationY = view.getRotationY();
            this.mRotationZ = view.getRotation();
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block3: {
                block2: {
                    if (!(object instanceof Transforms)) break block2;
                    object = (Transforms)object;
                    if (((Transforms)object).mTranslationX == this.mTranslationX && ((Transforms)object).mTranslationY == this.mTranslationY && ((Transforms)object).mTranslationZ == this.mTranslationZ && ((Transforms)object).mScaleX == this.mScaleX && ((Transforms)object).mScaleY == this.mScaleY && ((Transforms)object).mRotationX == this.mRotationX && ((Transforms)object).mRotationY == this.mRotationY && ((Transforms)object).mRotationZ == this.mRotationZ) break block3;
                }
                return false;
            }
            return true;
        }

        /*
         * Enabled aggressive block sorting
         */
        public int hashCode() {
            int n = 0;
            int n2 = this.mTranslationX != 0.0f ? Float.floatToIntBits(this.mTranslationX) : 0;
            int n3 = this.mTranslationY != 0.0f ? Float.floatToIntBits(this.mTranslationY) : 0;
            int n4 = this.mTranslationZ != 0.0f ? Float.floatToIntBits(this.mTranslationZ) : 0;
            int n5 = this.mScaleX != 0.0f ? Float.floatToIntBits(this.mScaleX) : 0;
            int n6 = this.mScaleY != 0.0f ? Float.floatToIntBits(this.mScaleY) : 0;
            int n7 = this.mRotationX != 0.0f ? Float.floatToIntBits(this.mRotationX) : 0;
            int n8 = this.mRotationY != 0.0f ? Float.floatToIntBits(this.mRotationY) : 0;
            if (this.mRotationZ != 0.0f) {
                n = Float.floatToIntBits(this.mRotationZ);
            }
            return ((((((n2 * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n8) * 31 + n;
        }

        public void restore(View view) {
            ChangeTransform.setTransforms(view, this.mTranslationX, this.mTranslationY, this.mTranslationZ, this.mScaleX, this.mScaleY, this.mRotationX, this.mRotationY, this.mRotationZ);
        }
    }

}

