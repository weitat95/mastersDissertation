/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NonNls
 */
package timber.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NonNls;

public final class Timber {
    private static final List<Tree> FOREST;
    private static final Tree[] TREE_ARRAY_EMPTY;
    private static final Tree TREE_OF_SOULS;
    static volatile Tree[] forestAsArray;

    static {
        TREE_ARRAY_EMPTY = new Tree[0];
        FOREST = new ArrayList<Tree>();
        forestAsArray = TREE_ARRAY_EMPTY;
        TREE_OF_SOULS = new Tree(){

            @Override
            public void d(String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].d(string2, arrobject);
                }
            }

            @Override
            public void d(Throwable throwable, String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].d(throwable, string2, arrobject);
                }
            }

            @Override
            public void e(String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].e(string2, arrobject);
                }
            }

            @Override
            public void e(Throwable throwable) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].e(throwable);
                }
            }

            @Override
            public void e(Throwable throwable, String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].e(throwable, string2, arrobject);
                }
            }

            @Override
            public void i(String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].i(string2, arrobject);
                }
            }

            @Override
            protected void log(int n, String string2, String string3, Throwable throwable) {
                throw new AssertionError((Object)"Missing override for log method.");
            }

            @Override
            public void v(String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].v(string2, arrobject);
                }
            }

            @Override
            public void v(Throwable throwable, String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].v(throwable, string2, arrobject);
                }
            }

            @Override
            public void w(String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].w(string2, arrobject);
                }
            }

            @Override
            public void w(Throwable throwable, String string2, Object ... arrobject) {
                Tree[] arrtree = forestAsArray;
                int n = arrtree.length;
                for (int i = 0; i < n; ++i) {
                    arrtree[i].w(throwable, string2, arrobject);
                }
            }
        };
    }

    public static void d(@NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.d(string2, arrobject);
    }

    public static void d(Throwable throwable, @NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.d(throwable, string2, arrobject);
    }

    public static void e(@NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.e(string2, arrobject);
    }

    public static void e(Throwable throwable) {
        TREE_OF_SOULS.e(throwable);
    }

    public static void e(Throwable throwable, @NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.e(throwable, string2, arrobject);
    }

    public static void i(@NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.i(string2, arrobject);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        }
        if (tree == TREE_OF_SOULS) {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
        List<Tree> list = FOREST;
        synchronized (list) {
            FOREST.add(tree);
            forestAsArray = FOREST.toArray(new Tree[FOREST.size()]);
            return;
        }
    }

    public static void v(@NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.v(string2, arrobject);
    }

    public static void v(Throwable throwable, @NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.v(throwable, string2, arrobject);
    }

    public static void w(@NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.w(string2, arrobject);
    }

    public static void w(Throwable throwable, @NonNls String string2, Object ... arrobject) {
        TREE_OF_SOULS.w(throwable, string2, arrobject);
    }

    public static abstract class Tree {
        final ThreadLocal<String> explicitTag = new ThreadLocal();

        private String getStackTraceString(Throwable throwable) {
            StringWriter stringWriter = new StringWriter(256);
            PrintWriter printWriter = new PrintWriter(stringWriter, false);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            return stringWriter.toString();
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void prepareLog(int n, Throwable throwable, String string2, Object ... arrobject) {
            void var4_6;
            String string3;
            block10: {
                String string4;
                block8: {
                    block9: {
                        block7: {
                            string3 = this.getTag();
                            if (!this.isLoggable(string3, n)) break block7;
                            string4 = string2;
                            if (string2 != null) {
                                string4 = string2;
                                if (string2.length() == 0) {
                                    string4 = null;
                                }
                            }
                            if (string4 != null) break block8;
                            if (throwable != null) break block9;
                        }
                        return;
                    }
                    String string5 = this.getStackTraceString(throwable);
                    break block10;
                }
                string2 = string4;
                if (arrobject.length > 0) {
                    string2 = this.formatMessage(string4, arrobject);
                }
                String string6 = string2;
                if (throwable != null) {
                    String string7 = string2 + "\n" + this.getStackTraceString(throwable);
                }
            }
            this.log(n, string3, (String)var4_6, throwable);
        }

        public void d(String string2, Object ... arrobject) {
            this.prepareLog(3, null, string2, arrobject);
        }

        public void d(Throwable throwable, String string2, Object ... arrobject) {
            this.prepareLog(3, throwable, string2, arrobject);
        }

        public void e(String string2, Object ... arrobject) {
            this.prepareLog(6, null, string2, arrobject);
        }

        public void e(Throwable throwable) {
            this.prepareLog(6, throwable, null, new Object[0]);
        }

        public void e(Throwable throwable, String string2, Object ... arrobject) {
            this.prepareLog(6, throwable, string2, arrobject);
        }

        protected String formatMessage(String string2, Object[] arrobject) {
            return String.format(string2, arrobject);
        }

        String getTag() {
            String string2 = this.explicitTag.get();
            if (string2 != null) {
                this.explicitTag.remove();
            }
            return string2;
        }

        public void i(String string2, Object ... arrobject) {
            this.prepareLog(4, null, string2, arrobject);
        }

        @Deprecated
        protected boolean isLoggable(int n) {
            return true;
        }

        protected boolean isLoggable(String string2, int n) {
            return this.isLoggable(n);
        }

        protected abstract void log(int var1, String var2, String var3, Throwable var4);

        public void v(String string2, Object ... arrobject) {
            this.prepareLog(2, null, string2, arrobject);
        }

        public void v(Throwable throwable, String string2, Object ... arrobject) {
            this.prepareLog(2, throwable, string2, arrobject);
        }

        public void w(String string2, Object ... arrobject) {
            this.prepareLog(5, null, string2, arrobject);
        }

        public void w(Throwable throwable, String string2, Object ... arrobject) {
            this.prepareLog(5, throwable, string2, arrobject);
        }
    }

}

