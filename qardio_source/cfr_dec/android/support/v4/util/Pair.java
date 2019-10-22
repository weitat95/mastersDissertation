/*
 * Decompiled with CFR 0.147.
 */
package android.support.v4.util;

public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public static <A, B> Pair<A, B> create(A a2, B b2) {
        return new Pair<A, B>(a2, b2);
    }

    private static boolean objectsEqual(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block3: {
            block2: {
                if (!(object instanceof Pair)) break block2;
                object = (Pair)object;
                if (Pair.objectsEqual(((Pair)object).first, this.first) && Pair.objectsEqual(((Pair)object).second, this.second)) break block3;
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
        int n2 = this.first == null ? 0 : this.first.hashCode();
        if (this.second == null) {
            return n2 ^ n;
        }
        n = this.second.hashCode();
        return n2 ^ n;
    }

    public String toString() {
        return "Pair{" + String.valueOf(this.first) + " " + String.valueOf(this.second) + "}";
    }
}

