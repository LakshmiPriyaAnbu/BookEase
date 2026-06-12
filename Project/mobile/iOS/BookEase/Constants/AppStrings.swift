import Foundation

// swiftlint:disable file_length type_body_length

/// Centralized string constants for the BookEase app.
/// Add new sections as features grow. Never hardcode user-visible strings in views.
enum AppStrings {

    // MARK: - Common

    enum Common {
        static let appName = "BookEase"
        static let orDivider = "OR"
        static let cancel = "Cancel"
        static let book = "Book"
        static let total = "Total"
        static let seeAll = "See all"
        static let getStarted = "Get started"
        static let backToHome = "Back to Home"
        static let browseServices = "Browse services"
        static let sendMessage = "Send message"
        static let tryAgain = "Try again"
        static let contactSupport = "Contact support"
        static let dismissNotification = "Dismiss notification"
        static let monthly = "Monthly"
        static let yearlyWithDiscount = "Yearly · -20%"
    }

    // MARK: - Navigation / Tab Bar

    enum Navigation {
        static let tabHome = "Home"
        static let tabExplore = "Explore"
        static let tabBookings = "Bookings"
        static let tabProfile = "Profile"
    }

    // MARK: - Home

    enum Home {
        static let greeting = "Good morning,"
        static let searchPlaceholder = "Search services or coaches"
        static let upcomingSessionLabel = "UPCOMING SESSION"
        static let upcomingSessionBadge = "In 2 hrs"
        static let popularServicesTitle = "Popular services"
        static let categoriesTitle = "Categories"
        static let howItWorksLink = "How it works"
        static let pricingLink = "Pricing"
        static let contactLink = "Contact"
        static let loginLink = "Log in"
    }

    // MARK: - Services

    enum Services {
        static let title = "Services"
        static let marketingSubtitle = "24 services across 6 categories"
        static let searchPlaceholder = "Search"
        static let noServicesFound = "No services found"
        static let noServicesFoundSubtitle = "Try adjusting your search or filters."
        static let whatsIncluded = "What's included"
        static let availableToday = "Available today"
        static let bookNow = "Book Now"
        static let browseByCategoryTitle = "Browse by category"
        static let featuredTitle = "Featured"
        static let searchServicesOrCoaches = "Search services or coaches"
    }

    // MARK: - Booking

    enum Booking {
        static let title = "Booking"
        static let selectDate = "Select date"
        static let selectTime = "Select time"
        static let yourDetails = "Your details"
        static let fullNamePlaceholder = "Jordan Hayes"
        static let phonePlaceholder = "+1 415 555 0199"
        static let notesPlaceholder = "Notes (optional)"
        static let confirmBookingButton = "Confirm booking · "
        static let addToCalendar = "Add to Calendar"
        static let errorSelectDate = "Please select a date"
        static let errorSelectTimeSlot = "Please select a time slot"
        static let toastBookingConfirmed = "Booking confirmed! Check your email."
    }

    // MARK: - Confirmation

    enum Confirmation {
        static let bookingConfirmedTitle = "Booking confirmed"
        static let bookingConfirmedMessage = "Your session is booked. We've sent the details to your email."
        static let bookingIdLabel = "Booking ID"
        static let serviceLabel = "Service"
        static let dateAndTimeLabel = "Date & time"
        static let coachLabel = "Coach"
    }

    // MARK: - Bookings (list)

    enum Bookings {
        static let title = "My bookings"
        static let emptyTitle = "No bookings yet"
        static let emptyMessage = "When you book a session it'll show up here. Find a coach and lock in your first one."
        static let emptyAction = "Browse services"
        static let swipeActionCancel = "Cancel"
    }

    // MARK: - Profile

    enum Profile {
        static let title = "Profile"
        static let myBookings = "My bookings"
        static let paymentMethods = "Payment methods"
        static let notifications = "Notifications"
        static let logOut = "Log out"
    }

    // MARK: - Auth

    enum Auth {
        static let welcomeBack = "Welcome back"
        static let loginSubtitle = "Log in to manage your bookings."
        static let emailLabel = "Email"
        static let emailPlaceholder = "your@email.com"
        static let emailAccessibilityLabel = "Email address"
        static let passwordLabel = "Password"
        static let passwordPlaceholder = "Password"
        static let forgotPassword = "Forgot?"
        static let forgotPasswordAccessibilityLabel = "Forgot password"
        static let showPasswordAccessibilityLabel = "Show password"
        static let hidePasswordAccessibilityLabel = "Hide password"
        static let loginButton = "Log in"
        static let continueWithGoogle = "Continue with Google"
        static let continueWithApple = "Continue with Apple"
        static let noAccountPrompt = "Don't have an account? "
        static let signUp = "Sign up"
        static let signUpAccessibilityLabel = "Sign up for an account"
        static let errorBannerMessage = "Please fix the highlighted fields before continuing."
        static let toastCheckCredentials = "Please check your email and password."
        static let toastWelcomeBack = "Welcome back!"
    }

    // MARK: - Contact

    enum Contact {
        static let title = "Contact"
        static let subtitle = "We reply within a day."
        static let emailInfoLabel = "Email"
        static let emailInfoValue = "hello@bookease.app"
        static let phoneInfoLabel = "Phone"
        static let phoneInfoValue = "+1 (415) 555-0100"
        static let studioInfoLabel = "Studio"
        static let studioInfoValue = "540 Market St, SF"
        static let sendMessageTitle = "Send a message"
        static let namePlaceholder = "Jordan Hayes"
        static let emailPlaceholder = "jordan@email.com"
        static let messagePlaceholder = "Your message…"
        static let sendMessageButton = "Send message"
        static let nameAccessibilityLabel = "Name"
        static let emailAccessibilityLabel = "Email"
        static let messageAccessibilityLabel = "Message"
        static let toastSuccess = "Message sent! We'll reply within a day."
        static let toastFillFields = "Please fill in all required fields."
        static let errorNameRequired = "Name is required"
        static let errorInvalidEmail = "Enter a valid email address"
        static let errorMessageTooShort = "Message must be at least 10 characters"
    }

    // MARK: - HowItWorks

    enum HowItWorks {
        static let eyebrow = "HOW IT WORKS"
        static let title = "Booked in three steps"
        static let subtitle = "From browsing to a confirmed session in under a minute — no calls, no waiting."
        static let getStartedButton = "Get started"
        static let step1Number = "1"
        static let step1Title = "Choose a service"
        static let step1Description = "Browse coaches and programs, filter by goal and schedule. Every coach is verified and reviewed."
        static let step2Number = "2"
        static let step2Title = "Pick a time slot"
        static let step2Description = "See live availability and lock the slot that fits your week. Reschedule free up to 12 hours before."
        static let step3Number = "3"
        static let step3Title = "Confirm & go"
        static let step3Description = "Pay securely in-app and get instant confirmation plus a calendar invite. Just show up and train."
    }

    // MARK: - Pricing

    enum Pricing {
        static let title = "Pricing"
        static let subtitle = "Start free or save with a plan."
        static let monthly = "Monthly"
        static let yearlyWithDiscount = "Yearly · -20%"
        static let starterPlanName = "Starter"
        static let starterPlanPrice = "₹0"
        static let starterPlanBillingCycle = "Pay per session"
        static let starterFeature1 = "Book any service"
        static let starterFeature2 = "Reminders & calendar sync"
        static let starterFeature3 = "In-app payments"
        static let starterCTAButton = "Get started"
        static let starterCTAAccessibilityLabel = "Get started with Starter plan"
        static let proPlanName = "Pro"
        static let proPlanPricingMonthly = "₹49"
        static let proPlanPricingYearly = "₹39"
        static let proPlanPerMonth = "/mo"
        static let proPlanBillingCycle = "8 sessions / month"
        static let proFeature1 = "Everything in Starter"
        static let proFeature2 = "Priority slots"
        static let proFeature3 = "Free rescheduling"
        static let proFeature4 = "Progress tracking"
        static let proCTAButton = "Choose Pro"
        static let mostPopularBadge = "MOST POPULAR"
        static let elitePlanName = "Elite"
        static let elitePlanPricingMonthly = "₹99"
        static let elitePlanPricingYearly = "₹79"
        static let elitePlanBillingCycle = "Unlimited sessions"
        static let eliteFeature1 = "Everything in Pro"
        static let eliteFeature2 = "Dedicated coach"
        static let eliteFeature3 = "Custom nutrition plan"
        static let eliteCTAButton = "Choose Elite"
    }

    // MARK: - NotFound

    enum NotFound {
        static let errorCode = "404"
        static let title = "Page not found"
        static let message = "This page may have moved or the link is broken. Let's get you back."
        static let backToHomeButton = "Back to home"
        static let browseServicesButton = "Browse services"
    }

    // MARK: - Errors

    enum Errors {
        static let defaultTitle = "Something went wrong"
        static let defaultMessage = "We hit a snag. Please try again in a moment."
        static let errorCodePrefix = "Error code: "
        static let validationEmailInvalid = "Enter a valid email address"
        static let validationPasswordTooShort = "Password must be at least 8 characters"
    }

    // MARK: - ServicesMarketing categories

    enum MarketingCategory {
        static let strengthTitle = "Strength"
        static let strengthCount = "8 services"
        static let hiitTitle = "HIIT"
        static let hiitCount = "5 services"
        static let mobilityTitle = "Mobility"
        static let mobilityCount = "4 services"
        static let nutritionTitle = "Nutrition"
        static let nutritionCount = "3 services"
    }

    // MARK: - ServicesMarketing featured rows

    enum FeaturedService {
        static let strengthCoachingTitle = "1:1 Strength Coaching"
        static let strengthCoachingDuration = "60 min"
        static let strengthCoachingRating = "4.9"
        static let strengthCoachingPrice = "₹65"
        static let nutritionConsultTitle = "Nutrition Consult"
        static let nutritionConsultDuration = "40 min"
        static let nutritionConsultRating = "5.0"
        static let nutritionConsultPrice = "₹55"
    }
}

// swiftlint:enable file_length type_body_length
