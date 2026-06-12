import SwiftUI

@main
struct BookEaseApp: App {
    @State private var toastManager = ToastManager()

    var body: some Scene {
        WindowGroup {
            RootTabView()
                .environment(toastManager)
                .toastOverlay(toastManager)
        }
    }
}
