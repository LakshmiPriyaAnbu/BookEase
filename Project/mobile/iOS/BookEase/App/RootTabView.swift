import SwiftUI

enum Tab: Int {
    case home, explore, bookings, profile
}

struct RootTabView: View {
    @State private var selectedTab: Tab = .home

    var body: some View {
        TabView(selection: $selectedTab) {
            NavigationStack {
                HomeView()
            }
            .tabItem {
                Label("Home", systemImage: selectedTab == .home ? "house.fill" : "house")
            }
            .tag(Tab.home)

            NavigationStack {
                ServiceListView()
            }
            .tabItem {
                Label("Explore", systemImage: selectedTab == .explore ? "magnifyingglass.circle.fill" : "magnifyingglass.circle")
            }
            .tag(Tab.explore)

            NavigationStack {
                BookingsView()
            }
            .tabItem {
                Label("Bookings", systemImage: "calendar")
            }
            .tag(Tab.bookings)

            NavigationStack {
                ProfileView()
            }
            .tabItem {
                Label("Profile", systemImage: selectedTab == .profile ? "person.fill" : "person")
            }
            .tag(Tab.profile)
        }
        .tint(.bePrimary)
    }
}

#Preview {
    RootTabView()
}
