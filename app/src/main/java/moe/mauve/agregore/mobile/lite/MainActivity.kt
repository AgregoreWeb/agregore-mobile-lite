package moe.mauve.agregore.mobile.lite

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import to.holepunch.bare.kit.Worklet

class MainActivity : Activity() {
  private var worklet: Worklet? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.fragment_agregore_browser_view)
    val browser: WebView = findViewById(R.id.browser)
    browser.webViewClient = MyWebViewClient()
    // myWebView.loadUrl("https://agregore.mauve.moe")

    val linkInput = findViewById<EditText>(R.id.linkInput)

    val submitButton = findViewById<Button>(R.id.submitButton)
    submitButton.setOnClickListener {
      val rawURL = linkInput.text.toString()
      val localGateway = "http://localhost:3748/hyper/" + rawURL.substring("hyper://".length)
      browser.loadUrl(localGateway)
    }

    worklet = Worklet(null)

    try {
      val args = arrayOf(applicationContext.filesDir.toString() + "/hyper", "Hello", "world")
      worklet!!.start("/app.bundle", assets.open("app.bundle"), args)
    } catch (e: Exception) {
      throw RuntimeException(e)
    }
  }

  override fun onPause() {
    super.onPause()

    worklet!!.suspend()
  }

  override fun onResume() {
    super.onResume()

    worklet!!.resume()
  }

  override fun onDestroy() {
    super.onDestroy()

    worklet!!.terminate()
    worklet = null
  }

  private class MyWebViewClient : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
      return false
    }
  }
}
