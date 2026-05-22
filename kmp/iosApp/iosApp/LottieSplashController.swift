import UIKit
import Lottie

public class LottieSplashController: UIViewController {
    public override func viewDidLoad() {
        super.viewDidLoad()
        
        view.backgroundColor = UIColor(red: 0.627, green: 0.102, blue: 0.122, alpha: 1.0)
        
        let animationView = LottieAnimationView(name: "cw")
        let size: CGFloat = 300
        animationView.frame = CGRect(
            x: (view.bounds.width - size) / 2,
            y: (view.bounds.height - size) / 2,
            width: size,
            height: size
        )
        animationView.contentMode = .scaleAspectFit
        animationView.loopMode = .playOnce
        animationView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        view.addSubview(animationView)
        animationView.play()
    }
}
