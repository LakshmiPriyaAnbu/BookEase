import SwiftUI

struct ServiceListView: View {
    var filterCategory: String? = nil
    @State private var vm = ServiceListViewModel()

    @Environment(ToastManager.self) private var toastManager

    var body: some View {
        VStack(spacing: 0) {
            // Top bar
            HStack {
                Text(AppStrings.Services.title)
                    .font(.jakarta(26, weight: .heavy))
                    .foregroundColor(.beInk800)
                Spacer()
                Button {} label: {
                    Image(systemName: "line.3.horizontal.decrease")
                        .font(.system(size: 19, weight: .medium))
                        .foregroundColor(.beInk700)
                        .frame(width: 40, height: 40)
                        .background(Color.white)
                        .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                        .overlay(RoundedRectangle(cornerRadius: Radius.md).stroke(Color.beBorder100, lineWidth: 1))
                }
            }
            .padding(.horizontal, Spacing.lg)
            .padding(.top, Spacing.sm)
            .padding(.bottom, Spacing.base)

            // Search
            HStack(spacing: 10) {
                Image(systemName: "magnifyingglass")
                    .font(.system(size: 17))
                    .foregroundColor(.beMuted500)
                TextField(AppStrings.Services.searchPlaceholder, text: $vm.searchText)
                    .font(.jakarta(14.5, weight: .medium))
                    .foregroundColor(.beInk700)
            }
            .frame(height: 46)
            .padding(.horizontal, Spacing.base)
            .background(Color.white)
            .clipShape(RoundedRectangle(cornerRadius: Radius.md))
            .overlay(RoundedRectangle(cornerRadius: Radius.md).stroke(Color.beBorder100, lineWidth: 1))
            .padding(.horizontal, Spacing.lg)
            .padding(.bottom, Spacing.base)

            // Filter chips
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: Spacing.sm) {
                    ForEach(vm.filterCategories, id: \.self) { cat in
                        let isSelected = vm.selectedCategory == cat
                        Button {
                            vm.selectedCategory = cat
                            if cat != "All" {
                                let count = vm.filteredServices.count
                                toastManager.show(
                                    "Showing \(count) \(cat) services",
                                    type: .info,
                                    duration: 2.0
                                )
                            }
                        } label: {
                            Text(cat)
                                .font(.jakarta(13, weight: isSelected ? .bold : .semibold))
                                .foregroundColor(isSelected ? .white : Color(hex: "545A66"))
                                .padding(.horizontal, 15)
                                .padding(.vertical, 8)
                                .background(isSelected ? Color.bePrimary : Color.white)
                                .clipShape(Capsule())
                                .overlay(Capsule().stroke(Color.beBorder100, lineWidth: isSelected ? 0 : 1))
                        }
                        .accessibilityLabel("\(cat) filter\(isSelected ? ", selected" : "")")
                    }
                }
                .padding(.horizontal, Spacing.lg)
            }
            .padding(.bottom, Spacing.lg)

            // List
            if vm.filteredServices.isEmpty {
                Spacer()
                VStack(spacing: Spacing.lg) {
                    Image(systemName: "magnifyingglass")
                        .font(.system(size: 44))
                        .foregroundColor(.beMuted400)
                        .accessibilityHidden(true)
                    Text(AppStrings.Services.noServicesFound)
                        .font(.beH3)
                        .foregroundColor(.beInk700)
                    Text(AppStrings.Services.noServicesFoundSubtitle)
                        .font(.beBodySm)
                        .foregroundColor(.beMuted500)
                }
                Spacer()
            } else {
                ScrollView {
                    LazyVStack(spacing: 13) {
                        ForEach(vm.filteredServices) { service in
                            NavigationLink(destination: ServiceDetailView(service: service)) {
                                ServiceRow(service: service)
                            }
                            .buttonStyle(.plain)
                            .padding(.horizontal, Spacing.lg)
                        }
                    }
                    .padding(.bottom, Spacing.xxl)
                }
            }
        }
        .background(Color.beAppBg)
        .navigationBarHidden(true)
        .onAppear {
            if let cat = filterCategory {
                vm.selectedCategory = cat
            }
        }
    }
}

#Preview {
    NavigationStack {
        ServiceListView()
    }
    .environment(ToastManager())
}
