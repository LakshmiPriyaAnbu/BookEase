import SwiftUI

struct BookingView: View {
    let service: Service
    let selectedSlot: TimeSlot?
    @State private var vm: BookingViewModel
    @State private var confirmedBooking: Booking? = nil
    @State private var showConfirmation = false

    @State private var showDateError = false
    @State private var showSlotError = false

    @Environment(ToastManager.self) private var toastManager

    init(service: Service, selectedSlot: TimeSlot?) {
        self.service = service
        self.selectedSlot = selectedSlot
        _vm = State(initialValue: BookingViewModel(service: service, preselectedSlot: selectedSlot))
    }

    var body: some View {
        ZStack(alignment: .bottom) {
            ScrollView {
                VStack(alignment: .leading, spacing: 0) {
                    // Title
                    Text(AppStrings.Booking.title)
                        .font(.jakarta(26, weight: .heavy))
                        .foregroundColor(.beInk800)
                        .padding(.horizontal, Spacing.lg)
                        .padding(.top, Spacing.sm)
                        .padding(.bottom, Spacing.lg)

                    // Date section
                    dateSection
                        .padding(.bottom, Spacing.xl)

                    // Time section
                    timeSection
                        .padding(.bottom, Spacing.xl)

                    // Details section
                    detailsSection
                        .padding(.bottom, Spacing.xl)

                    Color.clear.frame(height: 90)
                }
            }
            .background(Color.beAppBg)

            bottomBar
        }
        .navigationBarHidden(true)
        .navigationDestination(isPresented: $showConfirmation) {
            if let booking = confirmedBooking {
                ConfirmationView(booking: booking)
            }
        }
    }

    // MARK: - Date Selector

    private var dateSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Booking.selectDate)
                .font(.jakarta(14, weight: .heavy))
                .foregroundColor(.beInk700)
                .padding(.horizontal, Spacing.lg)

            HStack(spacing: Spacing.sm) {
                ForEach(Array(vm.datePills.enumerated()), id: \.offset) { index, pill in
                    DatePill(
                        weekday: pill.weekday,
                        day: pill.day,
                        isSelected: vm.selectedDateIndex == index
                    ) {
                        vm.selectedDateIndex = index
                        showDateError = false
                    }
                }
            }
            .padding(.horizontal, Spacing.lg)

            if showDateError {
                FieldErrorLabel(message: AppStrings.Booking.errorSelectDate)
                    .padding(.horizontal, Spacing.lg)
            }
        }
    }

    // MARK: - Time Grid

    private var timeSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Booking.selectTime)
                .font(.jakarta(14, weight: .heavy))
                .foregroundColor(.beInk700)
                .padding(.horizontal, Spacing.lg)

            LazyVGrid(
                columns: Array(repeating: GridItem(.flexible(), spacing: 10), count: 3),
                spacing: 10
            ) {
                ForEach(vm.timeSlots) { slot in
                    let isSelected = vm.selectedSlot?.id == slot.id
                    Button {
                        vm.selectedSlot = slot
                        showSlotError = false
                    } label: {
                        Text(slot.label)
                            .font(.jakarta(14, weight: .bold))
                            .foregroundColor(isSelected ? Color(hex: "4A3BD0") : .beInk700)
                            .frame(maxWidth: .infinity)
                            .frame(height: 46)
                            .background(isSelected ? Color.bePrimarySoft : Color.white)
                            .clipShape(RoundedRectangle(cornerRadius: 11))
                            .overlay(
                                RoundedRectangle(cornerRadius: 11)
                                    .stroke(isSelected ? Color.bePrimary : Color.beBorder100, lineWidth: isSelected ? 1.5 : 1)
                            )
                    }
                    .disabled(!slot.isAvailable)
                }
            }
            .padding(.horizontal, Spacing.lg)

            if showSlotError {
                FieldErrorLabel(message: AppStrings.Booking.errorSelectTimeSlot)
                    .padding(.horizontal, Spacing.lg)
            }
        }
    }

    // MARK: - Details Form

    private var detailsSection: some View {
        VStack(alignment: .leading, spacing: Spacing.md) {
            Text(AppStrings.Booking.yourDetails)
                .font(.jakarta(14, weight: .heavy))
                .foregroundColor(.beInk700)
                .padding(.horizontal, Spacing.lg)

            VStack(spacing: 10) {
                HStack {
                    Text(vm.fullName.isEmpty ? "Jordan Hayes" : vm.fullName)
                        .font(.jakarta(14.5, weight: .medium))
                        .foregroundColor(vm.fullName.isEmpty ? .beInk700 : .beInk700)
                    Spacer()
                }
                .frame(height: 46)
                .padding(.horizontal, Spacing.md)
                .background(Color.white)
                .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                .overlay(RoundedRectangle(cornerRadius: Radius.md).stroke(Color.beBorder100, lineWidth: 1))
                .overlay(
                    TextField(AppStrings.Booking.fullNamePlaceholder, text: $vm.fullName)
                        .font(.jakarta(14.5, weight: .medium))
                        .foregroundColor(.beInk700)
                        .padding(.horizontal, Spacing.md)
                )

                TextField(AppStrings.Booking.phonePlaceholder, text: $vm.phone)
                    .font(.jakarta(14.5, weight: .medium))
                    .foregroundColor(.beInk700)
                    .keyboardType(.phonePad)
                    .frame(height: 46)
                    .padding(.horizontal, Spacing.md)
                    .background(Color.white)
                    .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                    .overlay(RoundedRectangle(cornerRadius: Radius.md).stroke(Color.beBorder100, lineWidth: 1))

                ZStack(alignment: .topLeading) {
                    if vm.notes.isEmpty {
                        Text(AppStrings.Booking.notesPlaceholder)
                            .font(.jakarta(14, weight: .medium))
                            .foregroundColor(.beMuted500)
                            .padding(.top, 12)
                            .padding(.leading, 4)
                    }
                    TextEditor(text: $vm.notes)
                        .font(.jakarta(14, weight: .medium))
                        .foregroundColor(.beInk700)
                        .frame(minHeight: 64)
                        .scrollContentBackground(.hidden)
                }
                .padding(.horizontal, Spacing.md)
                .padding(.vertical, 6)
                .background(Color.white)
                .clipShape(RoundedRectangle(cornerRadius: Radius.md))
                .overlay(RoundedRectangle(cornerRadius: Radius.md).stroke(Color.beBorder100, lineWidth: 1))
            }
            .padding(.horizontal, Spacing.lg)
        }
    }

    // MARK: - Bottom Bar

    private var bottomBar: some View {
        Button {
            confirmBooking()
        } label: {
            Text(AppStrings.Booking.confirmBookingButton + service.formattedPrice)
                .font(.jakarta(15.5, weight: .bold))
                .foregroundColor(.white)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(vm.isValid ? Color.bePrimary : Color.beMuted400)
                .clipShape(RoundedRectangle(cornerRadius: Radius.lg))
                .shadow(color: Color.bePrimary.opacity(0.3), radius: 10, x: 0, y: 8)
        }
        .disabled(!vm.isValid)
        .padding(.horizontal, Spacing.lg)
        .padding(.vertical, Spacing.md)
        .padding(.bottom, 16)
        .background(
            Color.white
                .shadow(color: Color.black.opacity(0.08), radius: 12, x: 0, y: -4)
                .ignoresSafeArea()
        )
    }

    // MARK: - Booking Confirmation

    private func confirmBooking() {
        var hasErrors = false

        // Date is always preselected (index 0 by default), but we keep the error
        // hook in case the VM ever requires explicit selection. Currently datePills
        // always has entries and selectedDateIndex starts at 0, so this guard
        // stays consistent with the spec without breaking the existing UX.
        if vm.datePills.isEmpty {
            showDateError = true
            hasErrors = true
        } else {
            showDateError = false
        }

        if vm.selectedSlot == nil {
            showSlotError = true
            hasErrors = true
        } else {
            showSlotError = false
        }

        if hasErrors { return }

        let booking = vm.buildConfirmedBooking()
        confirmedBooking = booking
        toastManager.show(AppStrings.Booking.toastBookingConfirmed, type: .success)
        showConfirmation = true
    }
}

// MARK: - Date Pill

struct DatePill: View {
    let weekday: String
    let day: String
    let isSelected: Bool
    var action: () -> Void = {}

    var body: some View {
        Button(action: action) {
            VStack(spacing: 2) {
                Text(weekday)
                    .font(.jakarta(11, weight: .bold))
                    .foregroundColor(isSelected ? Color(hex: "D9D3FA") : .beMuted500)
                Text(day)
                    .font(.jakarta(17, weight: .heavy))
                    .foregroundColor(isSelected ? .white : .beInk700)
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 11)
            .background(
                isSelected
                ? AnyView(Color.bePrimary.shadow(.drop(color: Color.bePrimary.opacity(0.3), radius: 7, x: 0, y: 6)))
                : AnyView(Color.white)
            )
            .clipShape(RoundedRectangle(cornerRadius: 13))
            .overlay(
                RoundedRectangle(cornerRadius: 13)
                    .stroke(isSelected ? Color.clear : Color.beBorder100, lineWidth: 1)
            )
        }
    }
}

#Preview {
    NavigationStack {
        BookingView(service: MockData.services[0], selectedSlot: MockData.timeSlots.first)
    }
    .environment(ToastManager())
}
