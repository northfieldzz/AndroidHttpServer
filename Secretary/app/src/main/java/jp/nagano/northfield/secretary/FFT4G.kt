package jp.nagano.northfield.secretary

import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

//配列の要素数(2N)
internal class FFT4G(var n: Int) {
    // ビット反転に使用する作業領域
    private val ip = IntArray(2 + sqrt(0.5 * n).toInt() + 1)

    //cosとsinのテーブル(ip[0] == 0だと初期化される)
    private val w = DoubleArray(n * 5 / 4)

    // コンストラクタ
    init {
        ip[0] = 0
    }

    // publicメソッド
    //複素離散フーリエ変換
    fun cdft(isgn: Int, a: DoubleArray) {
        if (n > ip[0] shl 2) {
            makewt(n shr 2, ip, w)
        }
        if (n > 4) {
            if (isgn >= 0) {
                bitrv2(n, ip, a)
                cftfsub(n, a, w)
            } else {
                bitrv2conj(n, ip, a)
                cftbsub(n, a, w)
            }
        } else if (n == 4) {
            cftfsub(n, a, w)
        }
        return
    }

    //実数離散フーリエ変換
    fun rdft(isgn: Int, a: DoubleArray) {
        var nw: Int
        var nc: Int
        val xi: Double
        nw = ip[0]
        if (n > nw shl 2) {
            nw = n shr 2
            makewt(nw, ip, w)
        }
        nc = ip[1]
        if (n > nc shl 2) {
            nc = n shr 2
            makect(nc, ip, w, nw)
        }
        if (isgn >= 0) {
            if (n > 4) {
                bitrv2(n, ip, a)
                cftfsub(n, a, w)
                rftfsub(n, a, nc, w, nw)
            } else if (n == 4) {
                cftfsub(n, a, w)
            }
            xi = a[0] - a[1]
            a[0] += a[1]
            a[1] = xi
        } else {
            a[1] = 0.5 * (a[0] - a[1])
            a[0] -= a[1]
            if (n > 4) {
                rftbsub(n, a, nc, w, nw)
                bitrv2(n, ip, a)
                cftbsub(n, a, w)
            } else if (n == 4) {
                cftfsub(n, a, w)
            }
        }
    }

    //離散コサイン変換
    fun ddct(isgn: Int, a: DoubleArray) {
        var j: Int
        var nw: Int
        var nc: Int
        var xr: Double
        nw = ip[0]
        if (n > nw shl 2) {
            nw = n shr 2
            makewt(nw, ip, w)
        }
        nc = ip[1]
        if (n > nc) {
            nc = n
            makect(nc, ip, w, nw)
        }
        if (isgn < 0) {
            xr = a[n - 1]
            j = n - 2
            while (j >= 2) {
                a[j + 1] = a[j] - a[j - 1]
                a[j] += a[j - 1]
                j -= 2
            }
            a[1] = a[0] - xr
            a[0] += xr
            if (n > 4) {
                rftbsub(n, a, nc, w, nw)
                bitrv2(n, ip, a)
                cftbsub(n, a, w)
            } else if (n == 4) {
                cftfsub(n, a, w)
            }
        }
        dctsub(n, a, nc, w, nw)
        if (isgn >= 0) {
            if (n > 4) {
                bitrv2(n, ip, a)
                cftfsub(n, a, w)
                rftfsub(n, a, nc, w, nw)
            } else if (n == 4) {
                cftfsub(n, a, w)
            }
            xr = a[0] - a[1]
            a[0] += a[1]
            j = 2
            while (j < n) {
                a[j - 1] = a[j] - a[j + 1]
                a[j] += a[j + 1]
                j += 2
            }
            a[n - 1] = xr
        }
    }

    //離散サイン変換
    fun ddst(isgn: Int, a: DoubleArray) {
        var j: Int
        var nw: Int
        var nc: Int
        var xr: Double
        nw = ip[0]
        if (n > nw shl 2) {
            nw = n shr 2
            makewt(nw, ip, w)
        }
        nc = ip[1]
        if (n > nc) {
            nc = n
            makect(nc, ip, w, nw)
        }
        if (isgn < 0) {
            xr = a[n - 1]
            j = n - 2
            while (j >= 2) {
                a[j + 1] = -a[j] - a[j - 1]
                a[j] -= a[j - 1]
                j -= 2
            }
            a[1] = a[0] + xr
            a[0] -= xr
            if (n > 4) {
                rftbsub(n, a, nc, w, nw)
                bitrv2(n, ip, a)
                cftbsub(n, a, w)
            } else if (n == 4) {
                cftfsub(n, a, w)
            }
        }
        dstsub(n, a, nc, w, nw)
        if (isgn >= 0) {
            if (n > 4) {
                bitrv2(n, ip, a)
                cftfsub(n, a, w)
                rftfsub(n, a, nc, w, nw)
            } else if (n == 4) {
                cftfsub(n, a, w)
            }
            xr = a[0] - a[1]
            a[0] += a[1]
            j = 2
            while (j < n) {
                a[j - 1] = -a[j] - a[j + 1]
                a[j] -= a[j + 1]
                j += 2
            }
            a[n - 1] = -xr
        }
    }

    //実対称フーリエ変換を用いたコサイン変換
    fun dfct(a: DoubleArray, t: DoubleArray) {
        var j: Int
        var k: Int
        var l: Int
        var m: Int
        var mh: Int
        var nw: Int
        var nc: Int
        var xr: Double
        var xi: Double
        var yr: Double
        var yi: Double
        nw = ip[0]
        if (n > nw shl 3) {
            nw = n shr 3
            makewt(nw, ip, w)
        }
        nc = ip[1]
        if (n > nc shl 1) {
            nc = n shr 1
            makect(nc, ip, w, nw)
        }
        m = n shr 1
        yi = a[m]
        xi = a[0] + a[n]
        a[0] -= a[n]
        t[0] = xi - yi
        t[m] = xi + yi
        if (n > 2) {
            mh = m shr 1
            j = 1
            while (j < mh) {
                k = m - j
                xr = a[j] - a[n - j]
                xi = a[j] + a[n - j]
                yr = a[k] - a[n - k]
                yi = a[k] + a[n - k]
                a[j] = xr
                a[k] = yr
                t[j] = xi - yi
                t[k] = xi + yi
                j++
            }
            t[mh] = a[mh] + a[n - mh]
            a[mh] -= a[n - mh]
            dctsub(m, a, nc, w, nw)
            if (m > 4) {
                bitrv2(m, ip, a)
                cftfsub(m, a, w)
                rftfsub(m, a, nc, w, nw)
            } else if (m == 4) {
                cftfsub(m, a, w)
            }
            a[n - 1] = a[0] - a[1]
            a[1] = a[0] + a[1]
            j = m - 2
            while (j >= 2) {
                a[2 * j + 1] = a[j] + a[j + 1]
                a[2 * j - 1] = a[j] - a[j + 1]
                j -= 2
            }
            l = 2
            m = mh
            while (m >= 2) {
                dctsub(m, t, nc, w, nw)
                if (m > 4) {
                    bitrv2(m, ip, t)
                    cftfsub(m, t, w)
                    rftfsub(m, t, nc, w, nw)
                } else if (m == 4) {
                    cftfsub(m, t, w)
                }
                a[n - l] = t[0] - t[1]
                a[l] = t[0] + t[1]
                k = 0
                j = 2
                while (j < m) {
                    k += l shl 2
                    a[k - l] = t[j] - t[j + 1]
                    a[k + l] = t[j] + t[j + 1]
                    j += 2
                }
                l = l shl 1
                mh = m shr 1
                j = 0
                while (j < mh) {
                    k = m - j
                    t[j] = t[m + k] - t[m + j]
                    t[k] = t[m + k] + t[m + j]
                    j++
                }
                t[mh] = t[m + mh]
                m = mh
            }
            a[l] = t[0]
            a[n] = t[2] - t[1]
            a[0] = t[2] + t[1]
        } else {
            a[1] = a[0]
            a[2] = t[0]
            a[0] = t[1]
        }
    }

    //実非対称フーリエ変換を用いたサイン変換
    fun dfst(a: DoubleArray, t: DoubleArray) {
        var j: Int
        var k: Int
        var l: Int
        var m: Int
        var mh: Int
        var nw: Int
        var nc: Int
        var xr: Double
        var xi: Double
        var yr: Double
        var yi: Double
        nw = ip[0]
        if (n > nw shl 3) {
            nw = n shr 3
            makewt(nw, ip, w)
        }
        nc = ip[1]
        if (n > nc shl 1) {
            nc = n shr 1
            makect(nc, ip, w, nw)
        }
        if (n > 2) {
            m = n shr 1
            mh = m shr 1
            j = 1
            while (j < mh) {
                k = m - j
                xr = a[j] + a[n - j]
                xi = a[j] - a[n - j]
                yr = a[k] + a[n - k]
                yi = a[k] - a[n - k]
                a[j] = xr
                a[k] = yr
                t[j] = xi + yi
                t[k] = xi - yi
                j++
            }
            t[0] = a[mh] - a[n - mh]
            a[mh] += a[n - mh]
            a[0] = a[m]
            dstsub(m, a, nc, w, nw)
            if (m > 4) {
                bitrv2(m, ip, a)
                cftfsub(m, a, w)
                rftfsub(m, a, nc, w, nw)
            } else if (m == 4) {
                cftfsub(m, a, w)
            }
            a[n - 1] = a[1] - a[0]
            a[1] = a[0] + a[1]
            j = m - 2
            while (j >= 2) {
                a[2 * j + 1] = a[j] - a[j + 1]
                a[2 * j - 1] = -a[j] - a[j + 1]
                j -= 2
            }
            l = 2
            m = mh
            while (m >= 2) {
                dstsub(m, t, nc, w, nw)
                if (m > 4) {
                    bitrv2(m, ip, t)
                    cftfsub(m, t, w)
                    rftfsub(m, t, nc, w, nw)
                } else if (m == 4) {
                    cftfsub(m, t, w)
                }
                a[n - l] = t[1] - t[0]
                a[l] = t[0] + t[1]
                k = 0
                j = 2
                while (j < m) {
                    k += l shl 2
                    a[k - l] = -t[j] - t[j + 1]
                    a[k + l] = t[j] - t[j + 1]
                    j += 2
                }
                l = l shl 1
                mh = m shr 1
                j = 1
                while (j < mh) {
                    k = m - j
                    t[j] = t[m + k] + t[m + j]
                    t[k] = t[m + k] - t[m + j]
                    j++
                }
                t[0] = t[m + mh]
                m = mh
            }
            a[l] = t[0]
        }
        a[0] = 0.0
    }

    // privateメソッド
    private fun makewt(nw: Int, ip: IntArray, w: DoubleArray) {
        var j: Int
        val nwh: Int
        val delta: Double
        var x: Double
        var y: Double
        ip[0] = nw
        ip[1] = 1
        if (nw > 2) {
            nwh = nw shr 1
            delta = atan(1.0) / nwh
            w[0] = 1.0
            w[1] = 0.0
            w[nwh] = cos(delta * nwh)
            w[nwh + 1] = w[nwh]
            if (nwh > 2) {
                j = 2
                while (j < nwh) {
                    x = cos(delta * j)
                    y = sin(delta * j)
                    w[j] = x
                    w[j + 1] = y
                    w[nw - j] = y
                    w[nw - j + 1] = x
                    j += 2
                }
                bitrv2(nw, ip, w)
            }
        }
    }

    private fun makect(nc: Int, ip: IntArray, c: DoubleArray, nw: Int) {
        var j: Int
        val nch: Int
        val delta: Double
        ip[1] = nc
        if (nc > 1) {
            nch = nc shr 1
            delta = atan(1.0) / nch
            c[0 + nw] = cos(delta * nch)
            c[nch + nw] = 0.5 * c[0 + nw]
            j = 1
            while (j < nch) {
                c[j + nw] = 0.5 * cos(delta * j)
                c[nc - j + nw] = 0.5 * sin(delta * j)
                j++
            }
        }
    }

    private fun bitrv2(n: Int, ip: IntArray, a: DoubleArray) {
        var j: Int
        var j1: Int
        var k: Int
        var k1: Int
        var l: Int
        var m: Int
        var xr: Double
        var xi: Double
        var yr: Double
        var yi: Double
        ip[0 + 2] = 0
        l = n
        m = 1
        while (m shl 3 < l) {
            l = l shr 1
            j = 0
            while (j < m) {
                ip[m + j + 2] = ip[j + 2] + l
                j++
            }
            m = m shl 1
        }
        val m2: Int = 2 * m
        if (m shl 3 == l) {
            k = 0
            while (k < m) {
                j = 0
                while (j < k) {
                    j1 = 2 * j + ip[k + 2]
                    k1 = 2 * k + ip[j + 2]
                    xr = a[j1]
                    xi = a[j1 + 1]
                    yr = a[k1]
                    yi = a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 += 2 * m2
                    xr = a[j1]
                    xi = a[j1 + 1]
                    yr = a[k1]
                    yi = a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 -= m2
                    xr = a[j1]
                    xi = a[j1 + 1]
                    yr = a[k1]
                    yi = a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 += 2 * m2
                    xr = a[j1]
                    xi = a[j1 + 1]
                    yr = a[k1]
                    yi = a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j++
                }
                j1 = 2 * k + m2 + ip[k + 2]
                k1 = j1 + m2
                xr = a[j1]
                xi = a[j1 + 1]
                yr = a[k1]
                yi = a[k1 + 1]
                a[j1] = yr
                a[j1 + 1] = yi
                a[k1] = xr
                a[k1 + 1] = xi
                k++
            }
        } else {
            k = 1
            while (k < m) {
                j = 0
                while (j < k) {
                    j1 = 2 * j + ip[k + 2]
                    k1 = 2 * k + ip[j + 2]
                    xr = a[j1]
                    xi = a[j1 + 1]
                    yr = a[k1]
                    yi = a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 += m2
                    xr = a[j1]
                    xi = a[j1 + 1]
                    yr = a[k1]
                    yi = a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j++
                }
                k++
            }
        }
    }

    private fun bitrv2conj(n: Int, ip: IntArray, a: DoubleArray) {
        var j: Int
        var j1: Int
        var k: Int
        var k1: Int
        var l: Int
        var m: Int
        var xr: Double
        var xi: Double
        var yr: Double
        var yi: Double
        ip[0 + 2] = 0
        l = n
        m = 1
        while (m shl 3 < l) {
            l = l shr 1
            j = 0
            while (j < m) {
                ip[m + j + 2] = ip[j + 2] + l
                j++
            }
            m = m shl 1
        }
        val m2: Int = 2 * m
        if (m shl 3 == l) {
            k = 0
            while (k < m) {
                j = 0
                while (j < k) {
                    j1 = 2 * j + ip[k + 2]
                    k1 = 2 * k + ip[j + 2]
                    xr = a[j1]
                    xi = -a[j1 + 1]
                    yr = a[k1]
                    yi = -a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 += 2 * m2
                    xr = a[j1]
                    xi = -a[j1 + 1]
                    yr = a[k1]
                    yi = -a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 -= m2
                    xr = a[j1]
                    xi = -a[j1 + 1]
                    yr = a[k1]
                    yi = -a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 += 2 * m2
                    xr = a[j1]
                    xi = -a[j1 + 1]
                    yr = a[k1]
                    yi = -a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j++
                }
                k1 = 2 * k + ip[k + 2]
                a[k1 + 1] = -a[k1 + 1]
                j1 = k1 + m2
                k1 = j1 + m2
                xr = a[j1]
                xi = -a[j1 + 1]
                yr = a[k1]
                yi = -a[k1 + 1]
                a[j1] = yr
                a[j1 + 1] = yi
                a[k1] = xr
                a[k1 + 1] = xi
                k1 += m2
                a[k1 + 1] = -a[k1 + 1]
                k++
            }
        } else {
            a[1] = -a[1]
            a[m2 + 1] = -a[m2 + 1]
            k = 1
            while (k < m) {
                j = 0
                while (j < k) {
                    j1 = 2 * j + ip[k + 2]
                    k1 = 2 * k + ip[j + 2]
                    xr = a[j1]
                    xi = -a[j1 + 1]
                    yr = a[k1]
                    yi = -a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j1 += m2
                    k1 += m2
                    xr = a[j1]
                    xi = -a[j1 + 1]
                    yr = a[k1]
                    yi = -a[k1 + 1]
                    a[j1] = yr
                    a[j1 + 1] = yi
                    a[k1] = xr
                    a[k1 + 1] = xi
                    j++
                }
                k1 = 2 * k + ip[k + 2]
                a[k1 + 1] = -a[k1 + 1]
                a[k1 + m2 + 1] = -a[k1 + m2 + 1]
                k++
            }
        }
    }

    private fun cftfsub(n: Int, a: DoubleArray, w: DoubleArray) {
        var j: Int
        var j1: Int
        var j2: Int
        var j3: Int
        var l: Int
        var x0r: Double
        var x0i: Double
        var x1r: Double
        var x1i: Double
        var x2r: Double
        var x2i: Double
        var x3r: Double
        var x3i: Double
        l = 2
        if (n > 8) {
            cft1st(n, a, w)
            l = 8
            while (l shl 2 < n) {
                cftmdl(n, l, a, w)
                l = l shl 2
            }
        }
        if (l shl 2 == n) {
            j = 0
            while (j < l) {
                j1 = j + l
                j2 = j1 + l
                j3 = j2 + l
                x0r = a[j] + a[j1]
                x0i = a[j + 1] + a[j1 + 1]
                x1r = a[j] - a[j1]
                x1i = a[j + 1] - a[j1 + 1]
                x2r = a[j2] + a[j3]
                x2i = a[j2 + 1] + a[j3 + 1]
                x3r = a[j2] - a[j3]
                x3i = a[j2 + 1] - a[j3 + 1]
                a[j] = x0r + x2r
                a[j + 1] = x0i + x2i
                a[j2] = x0r - x2r
                a[j2 + 1] = x0i - x2i
                a[j1] = x1r - x3i
                a[j1 + 1] = x1i + x3r
                a[j3] = x1r + x3i
                a[j3 + 1] = x1i - x3r
                j += 2
            }
        } else {
            j = 0
            while (j < l) {
                j1 = j + l
                x0r = a[j] - a[j1]
                x0i = a[j + 1] - a[j1 + 1]
                a[j] += a[j1]
                a[j + 1] += a[j1 + 1]
                a[j1] = x0r
                a[j1 + 1] = x0i
                j += 2
            }
        }
    }

    private fun cftbsub(n: Int, a: DoubleArray, w: DoubleArray) {
        var j: Int
        var j1: Int
        var j2: Int
        var j3: Int
        var l: Int
        var x0r: Double
        var x0i: Double
        var x1r: Double
        var x1i: Double
        var x2r: Double
        var x2i: Double
        var x3r: Double
        var x3i: Double
        l = 2
        if (n > 8) {
            cft1st(n, a, w)
            l = 8
            while (l shl 2 < n) {
                cftmdl(n, l, a, w)
                l = l shl 2
            }
        }
        if (l shl 2 == n) {
            j = 0
            while (j < l) {
                j1 = j + l
                j2 = j1 + l
                j3 = j2 + l
                x0r = a[j] + a[j1]
                x0i = -a[j + 1] - a[j1 + 1]
                x1r = a[j] - a[j1]
                x1i = -a[j + 1] + a[j1 + 1]
                x2r = a[j2] + a[j3]
                x2i = a[j2 + 1] + a[j3 + 1]
                x3r = a[j2] - a[j3]
                x3i = a[j2 + 1] - a[j3 + 1]
                a[j] = x0r + x2r
                a[j + 1] = x0i - x2i
                a[j2] = x0r - x2r
                a[j2 + 1] = x0i + x2i
                a[j1] = x1r - x3i
                a[j1 + 1] = x1i - x3r
                a[j3] = x1r + x3i
                a[j3 + 1] = x1i + x3r
                j += 2
            }
        } else {
            j = 0
            while (j < l) {
                j1 = j + l
                x0r = a[j] - a[j1]
                x0i = -a[j + 1] + a[j1 + 1]
                a[j] += a[j1]
                a[j + 1] = -a[j + 1] - a[j1 + 1]
                a[j1] = x0r
                a[j1 + 1] = x0i
                j += 2
            }
        }
    }

    private fun cft1st(n: Int, a: DoubleArray, w: DoubleArray) {
        var k2: Int
        var wk1r: Double
        var wk1i: Double
        var wk2r: Double
        var wk2i: Double
        var wk3r: Double
        var wk3i: Double
        var x0r: Double
        var x0i: Double
        var x1r: Double
        var x1i: Double
        var x2r: Double
        var x2i: Double
        var x3r: Double
        var x3i: Double
        x0r = a[0] + a[2]
        x0i = a[1] + a[3]
        x1r = a[0] - a[2]
        x1i = a[1] - a[3]
        x2r = a[4] + a[6]
        x2i = a[5] + a[7]
        x3r = a[4] - a[6]
        x3i = a[5] - a[7]
        a[0] = x0r + x2r
        a[1] = x0i + x2i
        a[4] = x0r - x2r
        a[5] = x0i - x2i
        a[2] = x1r - x3i
        a[3] = x1i + x3r
        a[6] = x1r + x3i
        a[7] = x1i - x3r
        wk1r = w[2]
        x0r = a[8] + a[10]
        x0i = a[9] + a[11]
        x1r = a[8] - a[10]
        x1i = a[9] - a[11]
        x2r = a[12] + a[14]
        x2i = a[13] + a[15]
        x3r = a[12] - a[14]
        x3i = a[13] - a[15]
        a[8] = x0r + x2r
        a[9] = x0i + x2i
        a[12] = x2i - x0i
        a[13] = x0r - x2r
        x0r = x1r - x3i
        x0i = x1i + x3r
        a[10] = wk1r * (x0r - x0i)
        a[11] = wk1r * (x0r + x0i)
        x0r = x3i + x1r
        x0i = x3r - x1i
        a[14] = wk1r * (x0i - x0r)
        a[15] = wk1r * (x0i + x0r)
        var k1 = 0
        var j = 16
        while (j < n) {
            k1 += 2
            k2 = 2 * k1
            wk2r = w[k1]
            wk2i = w[k1 + 1]
            wk1r = w[k2]
            wk1i = w[k2 + 1]
            wk3r = wk1r - 2 * wk2i * wk1i
            wk3i = 2 * wk2i * wk1r - wk1i
            x0r = a[j] + a[j + 2]
            x0i = a[j + 1] + a[j + 3]
            x1r = a[j] - a[j + 2]
            x1i = a[j + 1] - a[j + 3]
            x2r = a[j + 4] + a[j + 6]
            x2i = a[j + 5] + a[j + 7]
            x3r = a[j + 4] - a[j + 6]
            x3i = a[j + 5] - a[j + 7]
            a[j] = x0r + x2r
            a[j + 1] = x0i + x2i
            x0r -= x2r
            x0i -= x2i
            a[j + 4] = wk2r * x0r - wk2i * x0i
            a[j + 5] = wk2r * x0i + wk2i * x0r
            x0r = x1r - x3i
            x0i = x1i + x3r
            a[j + 2] = wk1r * x0r - wk1i * x0i
            a[j + 3] = wk1r * x0i + wk1i * x0r
            x0r = x1r + x3i
            x0i = x1i - x3r
            a[j + 6] = wk3r * x0r - wk3i * x0i
            a[j + 7] = wk3r * x0i + wk3i * x0r
            wk1r = w[k2 + 2]
            wk1i = w[k2 + 3]
            wk3r = wk1r - 2 * wk2r * wk1i
            wk3i = 2 * wk2r * wk1r - wk1i
            x0r = a[j + 8] + a[j + 10]
            x0i = a[j + 9] + a[j + 11]
            x1r = a[j + 8] - a[j + 10]
            x1i = a[j + 9] - a[j + 11]
            x2r = a[j + 12] + a[j + 14]
            x2i = a[j + 13] + a[j + 15]
            x3r = a[j + 12] - a[j + 14]
            x3i = a[j + 13] - a[j + 15]
            a[j + 8] = x0r + x2r
            a[j + 9] = x0i + x2i
            x0r -= x2r
            x0i -= x2i
            a[j + 12] = -wk2i * x0r - wk2r * x0i
            a[j + 13] = -wk2i * x0i + wk2r * x0r
            x0r = x1r - x3i
            x0i = x1i + x3r
            a[j + 10] = wk1r * x0r - wk1i * x0i
            a[j + 11] = wk1r * x0i + wk1i * x0r
            x0r = x1r + x3i
            x0i = x1i - x3r
            a[j + 14] = wk3r * x0r - wk3i * x0i
            a[j + 15] = wk3r * x0i + wk3i * x0r
            j += 16
        }
    }

    private fun cftmdl(n: Int, l: Int, a: DoubleArray, w: DoubleArray) {
        var j: Int
        var j1: Int
        var j2: Int
        var j3: Int
        var k: Int
        var k2: Int
        val m2: Int
        var wk1r: Double
        var wk1i: Double
        var wk2r: Double
        var wk2i: Double
        var wk3r: Double
        var wk3i: Double
        var x0r: Double
        var x0i: Double
        var x1r: Double
        var x1i: Double
        var x2r: Double
        var x2i: Double
        var x3r: Double
        var x3i: Double
        val m: Int = l shl 2
        j = 0
        while (j < l) {
            j1 = j + l
            j2 = j1 + l
            j3 = j2 + l
            x0r = a[j] + a[j1]
            x0i = a[j + 1] + a[j1 + 1]
            x1r = a[j] - a[j1]
            x1i = a[j + 1] - a[j1 + 1]
            x2r = a[j2] + a[j3]
            x2i = a[j2 + 1] + a[j3 + 1]
            x3r = a[j2] - a[j3]
            x3i = a[j2 + 1] - a[j3 + 1]
            a[j] = x0r + x2r
            a[j + 1] = x0i + x2i
            a[j2] = x0r - x2r
            a[j2 + 1] = x0i - x2i
            a[j1] = x1r - x3i
            a[j1 + 1] = x1i + x3r
            a[j3] = x1r + x3i
            a[j3 + 1] = x1i - x3r
            j += 2
        }
        wk1r = w[2]
        j = m
        while (j < l + m) {
            j1 = j + l
            j2 = j1 + l
            j3 = j2 + l
            x0r = a[j] + a[j1]
            x0i = a[j + 1] + a[j1 + 1]
            x1r = a[j] - a[j1]
            x1i = a[j + 1] - a[j1 + 1]
            x2r = a[j2] + a[j3]
            x2i = a[j2 + 1] + a[j3 + 1]
            x3r = a[j2] - a[j3]
            x3i = a[j2 + 1] - a[j3 + 1]
            a[j] = x0r + x2r
            a[j + 1] = x0i + x2i
            a[j2] = x2i - x0i
            a[j2 + 1] = x0r - x2r
            x0r = x1r - x3i
            x0i = x1i + x3r
            a[j1] = wk1r * (x0r - x0i)
            a[j1 + 1] = wk1r * (x0r + x0i)
            x0r = x3i + x1r
            x0i = x3r - x1i
            a[j3] = wk1r * (x0i - x0r)
            a[j3 + 1] = wk1r * (x0i + x0r)
            j += 2
        }
        var k1 = 0
        m2 = 2 * m
        k = m2
        while (k < n) {
            k1 += 2
            k2 = 2 * k1
            wk2r = w[k1]
            wk2i = w[k1 + 1]
            wk1r = w[k2]
            wk1i = w[k2 + 1]
            wk3r = wk1r - 2 * wk2i * wk1i
            wk3i = 2 * wk2i * wk1r - wk1i
            j = k
            while (j < l + k) {
                j1 = j + l
                j2 = j1 + l
                j3 = j2 + l
                x0r = a[j] + a[j1]
                x0i = a[j + 1] + a[j1 + 1]
                x1r = a[j] - a[j1]
                x1i = a[j + 1] - a[j1 + 1]
                x2r = a[j2] + a[j3]
                x2i = a[j2 + 1] + a[j3 + 1]
                x3r = a[j2] - a[j3]
                x3i = a[j2 + 1] - a[j3 + 1]
                a[j] = x0r + x2r
                a[j + 1] = x0i + x2i
                x0r -= x2r
                x0i -= x2i
                a[j2] = wk2r * x0r - wk2i * x0i
                a[j2 + 1] = wk2r * x0i + wk2i * x0r
                x0r = x1r - x3i
                x0i = x1i + x3r
                a[j1] = wk1r * x0r - wk1i * x0i
                a[j1 + 1] = wk1r * x0i + wk1i * x0r
                x0r = x1r + x3i
                x0i = x1i - x3r
                a[j3] = wk3r * x0r - wk3i * x0i
                a[j3 + 1] = wk3r * x0i + wk3i * x0r
                j += 2
            }
            wk1r = w[k2 + 2]
            wk1i = w[k2 + 3]
            wk3r = wk1r - 2 * wk2r * wk1i
            wk3i = 2 * wk2r * wk1r - wk1i
            j = k + m
            while (j < l + (k + m)) {
                j1 = j + l
                j2 = j1 + l
                j3 = j2 + l
                x0r = a[j] + a[j1]
                x0i = a[j + 1] + a[j1 + 1]
                x1r = a[j] - a[j1]
                x1i = a[j + 1] - a[j1 + 1]
                x2r = a[j2] + a[j3]
                x2i = a[j2 + 1] + a[j3 + 1]
                x3r = a[j2] - a[j3]
                x3i = a[j2 + 1] - a[j3 + 1]
                a[j] = x0r + x2r
                a[j + 1] = x0i + x2i
                x0r -= x2r
                x0i -= x2i
                a[j2] = -wk2i * x0r - wk2r * x0i
                a[j2 + 1] = -wk2i * x0i + wk2r * x0r
                x0r = x1r - x3i
                x0i = x1i + x3r
                a[j1] = wk1r * x0r - wk1i * x0i
                a[j1 + 1] = wk1r * x0i + wk1i * x0r
                x0r = x1r + x3i
                x0i = x1i - x3r
                a[j3] = wk3r * x0r - wk3i * x0i
                a[j3 + 1] = wk3r * x0i + wk3i * x0r
                j += 2
            }
            k += m2
        }
    }

    private fun rftfsub(n: Int, a: DoubleArray, nc: Int, c: DoubleArray, nw: Int) {
        var k: Int
        val ks: Int
        var wkr: Double
        var wki: Double
        var xr: Double
        var xi: Double
        var yr: Double
        var yi: Double
        val m: Int = n shr 1
        ks = 2 * nc / m
        var kk = 0
        var j = 2
        while (j < m) {
            k = n - j
            kk += ks
            wkr = 0.5 - c[nc - kk + nw]
            wki = c[kk + nw]
            xr = a[j] - a[k]
            xi = a[j + 1] + a[k + 1]
            yr = wkr * xr - wki * xi
            yi = wkr * xi + wki * xr
            a[j] -= yr
            a[j + 1] -= yi
            a[k] += yr
            a[k + 1] -= yi
            j += 2
        }
    }

    private fun rftbsub(n: Int, a: DoubleArray, nc: Int, c: DoubleArray, nw: Int) {
        var k: Int
        val ks: Int
        var wkr: Double
        var wki: Double
        var xr: Double
        var xi: Double
        var yr: Double
        var yi: Double
        a[1] = -a[1]
        val m: Int = n shr 1
        ks = 2 * nc / m
        var kk = 0
        var j = 2
        while (j < m) {
            k = n - j
            kk += ks
            wkr = 0.5 - c[nc - kk + nw]
            wki = c[kk + nw]
            xr = a[j] - a[k]
            xi = a[j + 1] + a[k + 1]
            yr = wkr * xr + wki * xi
            yi = wkr * xi - wki * xr
            a[j] -= yr
            a[j + 1] = yi - a[j + 1]
            a[k] += yr
            a[k + 1] = yi - a[k + 1]
            j += 2
        }
        a[m + 1] = -a[m + 1]
    }

    private fun dctsub(n: Int, a: DoubleArray, nc: Int, c: DoubleArray, nw: Int) {
        var k: Int
        var wkr: Double
        var wki: Double
        var xr: Double
        val m: Int = n shr 1
        val ks: Int = nc / n
        var kk = 0
        var j = 1
        while (j < m) {
            k = n - j
            kk += ks
            wkr = c[kk + nw] - c[nc - kk + nw]
            wki = c[kk + nw] + c[nc - kk + nw]
            xr = wki * a[j] - wkr * a[k]
            a[j] = wkr * a[j] + wki * a[k]
            a[k] = xr
            j++
        }
        a[m] *= c[0 + nw]
    }

    private fun dstsub(n: Int, a: DoubleArray, nc: Int, c: DoubleArray, nw: Int) {
        var k: Int
        var wkr: Double
        var wki: Double
        var xr: Double
        val m: Int = n shr 1
        val ks: Int = nc / n
        var kk = 0
        var j = 1
        while (j < m) {
            k = n - j
            kk += ks
            wkr = c[kk + nw] - c[nc - kk + nw]
            wki = c[kk + nw] + c[nc - kk + nw]
            xr = wki * a[k] - wkr * a[j]
            a[k] = wkr * a[k] + wki * a[j]
            a[j] = xr
            j++
        }
        a[m] *= c[0 + nw]
    }
}
