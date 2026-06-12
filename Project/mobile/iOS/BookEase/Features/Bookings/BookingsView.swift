import SwiftUI

struct BookingsView: View {
    @State private var vm = BookingsViewModel()

    var body: some View {
        VStack(spacing: 0) {
            // Title
            HStack {
                Text(AppStrings.Bookings.title)
                    .font(.jakarta(26, weight: .heavy))
                    .foregroundColor(.beInk800)
                Spacer()
            }
            .padding(.horizontal, Spacing.lg)
            .padding(.top, Spacing.sm)
            .padding(.bottom, Spacing.base)

            // Segmented picker
            segmentedPicker
                .padding(.horizontal, Spacing.lg)
                .padding(.bottom, Spacing.lg)

            if vm.filteredBookings.isEmpty {
                emptyStateView
            } else {
                ScrollView {
                    LazyVStack(spacing: 12) {
                        ForEach(vm.filteredBookings) { booking in
                            BookingCard(booking: booking)
                                .padding(.horizontal, Spacing.lg)
                                .swipeActions(edge: .trailing, allowsFullSwipe: false) {
                                    if booking.status == .confirmed || booking.status == .pending {
                                        Button(role: .destructive) {
                                            vm.cancelBooking(id: booking.id)
                                        } label: {
                                            Label(AppStrings.Bookings.swipeActionCancel, systemImage: "xmark.circle")
                                        }
                                    }
                                }
                        }
                    }
                    .padding(.bottom, Spacing.xxl)
                }
            }
        }
        .background(Color.beAppBg)
        .navigationBarHidden(true)
    }

    // MARK: - Segmented Picker

    private var segmentedPicker: some View {
        HStack(spacing: 0) {
            ForEach(BookingSegment.allCases, id: \.self) { segment in
                Button {
                    withAnimation(.easeInOut(duration: 0.2)) {
                        vm.selectedSegment = segment
                    }
                } label: {
                    Text(segment.rawValue)
                        .font(.jakarta(13.5, weight: vm.selectedSegment == segment ? .bold : .semibold))
                        .foregroundColor(vm.selectedSegment == segment ? .beInk700 : .beMuted500)
                        .frame(maxWidth: .infinity)
                        .frame(height: 36)
                        .background(
                            vm.selectedSegment == segment
                            ? Color.white
                                .clipShape(RoundedRectangle(cornerRadius: 9))
                                .shadow(color: Color.black.opacity(0.08), radius: 3, x: 0, y: 1)
                            : Color.clear
                        )
                }
            }
        }
        .padding(4)
        .background(Color(hex: "ECECEF"))
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }

    // MARK: - Empty State

    private var emptyStateView: some View {
        EmptyStateView(
            variant: .bookings,
            title: AppStrings.Bookings.emptyTitle,
            message: AppStrings.Bookings.emptyMessage,
            actionTitle: AppStrings.Bookings.emptyAction,
            onAction: { /* navigate to browse */ }
        )
    }
}

#Preview {
    NavigationStack {
        BookingsView()
    }
}
