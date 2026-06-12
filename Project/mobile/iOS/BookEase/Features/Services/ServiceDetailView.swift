import SwiftUI

struct ServiceDetailView: View {
    let service: Service
    @State private var vm: ServiceDetailViewModel
    @Environment(\.dismiss) private var dismiss

    init(service: Service) {
        self.service = service
        _vm = State(initialValue: ServiceDetailViewModel(service: service))
    }

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView {
                VStack(alignment: .leading, spacing: 0) {
                    heroSection

                    VStack(alignment: .leading, spacing: Spacing.lg) {
                        titleSection
                            .padding(.top, Spacing.lg)

                        metaRow

                        Text(service.description)
                            .font(.jakarta(14.5, weight: .regular))
                            .foregroundColor(.beText600)
                            .lineSpacing(4)

                        includedSection

                        timeSlotsSection

                        Color.clear.frame(height: 90)
                    }
                    .padding(.horizontal, Spacing.lg)
                }
            }
            .background(Color.white)
            .ignoresSafeArea(edges: .top)

            bottomActionBar
        }
        .navigationBarHidden(true)
        .overlay(alignment: .topLeading) {
            backButton
        }
    }

    // MARK: - Hero

    private var heroSection: some View {
        ServiceThumbnail(imageUrl: service.imageUrl, size: 300)
            .frame(maxWidth: .infinity)
            .frame(height: 250)
    }

    // MARK: - Back Button

    private var backButton: some View {
        Button(action: { dismiss() }) {
            Image(systemName: "chevron.left")
                .font(.system(size: 12, weight: .semibold))
                .foregroundColor(.beInk700)
                .frame(width: 38, height: 38)
                .background(Color.white.opacity(0.92))
                .clipShape(Circle())
                .shadow(color: Color.black.opacity(0.1), radius: 8, x: 0, y: 2)
        }
        .padding(.leading, Spacing.base)
        .padding(.top, 54)
    }

    // MARK: - Title

    private var titleSection: some View {
        HStack(alignment: .top, spacing: Spacing.md) {
            Text(service.name)
                .font(.jakarta(22, weight: .heavy))
                .foregroundColor(.beInk800)
                .fixedSize(horizontal: false, vertical: true)

            Spacer()

            Text(service.formattedPrice)
                .font(.jakarta(22, weight: .heavy))
                .foregroundColor(.bePrimary)
        }
    }

    // MARK: - Meta Row

    private var metaRow: some View {
        HStack(spacing: Spacing.base) {
            Text("⏱ \(service.formattedDuration)")
                .font(.jakarta(13.5, weight: .semibold))
                .foregroundColor(.beText600)
            Text("★ \(String(format: "%.1f", service.rating))")
                .font(.jakarta(13.5, weight: .semibold))
                .foregroundColor(.beText600)
            Text(service.coachName)
                .font(.jakarta(13.5, weight: .semibold))
                .foregroundColor(.beText600)
        }
    }

    // MARK: - Included

    private var includedSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Services.whatsIncluded)
                .font(.jakarta(15, weight: .heavy))
                .foregroundColor(.beInk800)

            ForEach(service.included, id: \.self) { item in
                HStack(spacing: 10) {
                    Image(systemName: "checkmark")
                        .font(.system(size: 11, weight: .bold))
                        .foregroundColor(.beSuccess)
                        .frame(width: 20, height: 20)
                        .background(Color.beSuccessBg)
                        .clipShape(Circle())

                    Text(item)
                        .font(.jakarta(14, weight: .medium))
                        .foregroundColor(.beInk700)
                }
            }
        }
    }

    // MARK: - Time Slots

    private var timeSlotsSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Services.availableToday)
                .font(.jakarta(15, weight: .heavy))
                .foregroundColor(.beInk800)

            HStack(spacing: 9) {
                ForEach(vm.timeSlots.prefix(4)) { slot in
                    let isSelected = vm.selectedSlot?.id == slot.id
                    Button {
                        if slot.isAvailable { vm.selectedSlot = slot }
                    } label: {
                        Text(slot.label)
                            .font(.jakarta(13.5, weight: .bold))
                            .foregroundColor(isSelected ? Color(hex: "4A3BD0") : .beInk700)
                            .padding(.horizontal, 15)
                            .padding(.vertical, 9)
                            .background(isSelected ? Color.bePrimarySoft : Color.white)
                            .clipShape(RoundedRectangle(cornerRadius: 10))
                            .overlay(
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(isSelected ? Color.bePrimary : Color.beBorder100, lineWidth: isSelected ? 1.5 : 1)
                            )
                    }
                    .disabled(!slot.isAvailable)
                }
            }
        }
    }

    // MARK: - Bottom Bar

    private var bottomActionBar: some View {
        HStack(spacing: Spacing.lg) {
            VStack(alignment: .leading, spacing: 2) {
                Text(AppStrings.Common.total)
                    .font(.jakarta(11.5, weight: .semibold))
                    .foregroundColor(.beMuted500)
                Text(service.formattedPrice)
                    .font(.jakarta(19, weight: .heavy))
                    .foregroundColor(.beInk800)
            }

            NavigationLink(destination: BookingView(service: service, selectedSlot: vm.selectedSlot)) {
                Text(AppStrings.Services.bookNow)
                    .font(.jakarta(15.5, weight: .bold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .frame(height: 52)
                    .background(vm.canProceed ? Color.bePrimary : Color.beMuted400)
                    .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                    .shadow(color: Color.bePrimary.opacity(0.3), radius: 10, x: 0, y: 8)
            }
            .disabled(!vm.canProceed)
        }
        .padding(.horizontal, Spacing.lg)
        .padding(.vertical, Spacing.md)
        .padding(.bottom, 16)
        .background(
            Color.white
                .shadow(color: Color.black.opacity(0.08), radius: 12, x: 0, y: -4)
                .ignoresSafeArea()
        )
    }
}

// MARK: - Time Slot Chip (used in ServiceDetail grid fallback)

struct TimeSlotChip: View {
    let slot: TimeSlot
    let isSelected: Bool
    var action: () -> Void = {}

    var body: some View {
        Button(action: action) {
            Text(slot.label)
                .font(.beLabel)
                .foregroundColor(
                    !slot.isAvailable ? .beMuted400 :
                    isSelected ? Color(hex: "4A3BD0") : .beText600
                )
                .frame(maxWidth: .infinity)
                .frame(height: 42)
                .background(
                    isSelected ? Color.bePrimarySoft :
                    !slot.isAvailable ? Color.beSurface200 : Color.white
                )
                .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                .overlay(
                    RoundedRectangle(cornerRadius: Radius.md)
                        .stroke(
                            isSelected ? Color.bePrimary : Color.beBorder200,
                            lineWidth: isSelected ? 1.5 : 1
                        )
                )
        }
        .disabled(!slot.isAvailable)
    }
}

#Preview {
    NavigationStack {
        ServiceDetailView(service: MockData.services[0])
    }
}
