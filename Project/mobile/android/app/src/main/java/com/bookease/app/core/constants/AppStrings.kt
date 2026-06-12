package com.bookease.app.core.constants

/**
 * Centralized string constants for non-UI logic (ViewModels, repositories, etc.).
 * Composables should use stringResource(R.string.key) for displayed text.
 * Use these constants only for programmatic logic that runs outside of a Composable context.
 */
object AppStrings {

    object Common {
        const val APP_NAME = "BookEase"
        const val BOOK = "Book"
        const val BOOK_NOW = "Book Now"
        const val GET_STARTED = "Get started"
        const val BACK_TO_HOME = "Back to home"
        const val BROWSE_SERVICES = "Browse services"
        const val SEND_MESSAGE = "Send message"
        const val SEARCH_PLACEHOLDER = "Search services or coaches…"
        const val SEARCH = "Search"
        const val TOTAL = "Total"
        const val CANCEL = "Cancel"
        const val OR_DIVIDER = "OR"
        const val SEE_ALL = "See all"
    }

    object Navigation {
        const val HOME = "Home"
        const val EXPLORE = "Explore"
        const val BOOKINGS = "Bookings"
        const val PROFILE = "Profile"
    }

    object Home {
        const val GREETING = "Good morning,"
        const val SEARCH_PLACEHOLDER = "Search services or coaches"
        const val UPCOMING_SESSION_LABEL = "UPCOMING SESSION"
        const val UPCOMING_SESSION_ETA = "In 2 hrs"
        const val POPULAR_SERVICES = "Popular services"
        const val CATEGORIES = "Categories"
        const val MORE = "More"
        const val HOW_IT_WORKS = "How it works"
        const val PRICING = "Pricing"
        const val CONTACT = "Contact"
        const val LOG_IN = "Log in"
        const val NOTIFICATIONS_CONTENT_DESC = "Notifications"
    }

    object Services {
        const val TITLE = "Services"
        const val SUBTITLE = "24 services across 6 categories"
        const val SEARCH_PLACEHOLDER = "Search services or coaches…"
        const val SEARCH_CONTENT_DESC = "Search"
        const val BROWSE_BY_CATEGORY = "Browse by category"
        const val FEATURED = "Featured"
        const val CATEGORY_SERVICE_COUNT_FORMAT = "%d services"
    }

    object Bookings {
        const val TITLE = "My bookings"
        const val SEGMENT_UPCOMING = "Upcoming"
        const val SEGMENT_COMPLETED = "Completed"
        const val SEGMENT_CANCELLED = "Cancelled"
        const val EMPTY_UPCOMING_TITLE = "No upcoming bookings"
        const val EMPTY_COMPLETED_TITLE = "No completed sessions"
        const val EMPTY_CANCELLED_TITLE = "No cancelled bookings"
        const val EMPTY_GENERIC_TITLE = "Nothing here"
        const val EMPTY_UPCOMING_MESSAGE = "Book a session to get started"
        const val EMPTY_COMPLETED_MESSAGE = "Your finished sessions will appear here"
        const val EMPTY_CANCELLED_MESSAGE = "Cancelled bookings will appear here"
        const val EMPTY_GENERIC_MESSAGE = ""
        const val SWIPE_CANCEL = "Cancel"
        const val ACTION_BROWSE_SERVICES = "Browse services"
    }

    object Booking {
        const val TITLE = "Booking"
        const val SELECT_DATE = "Select date"
        const val SELECT_TIME = "Select time"
        const val YOUR_DETAILS = "Your details"
        const val PLACEHOLDER_FULL_NAME = "Full name"
        const val PLACEHOLDER_PHONE = "+1 415 555 0199"
        const val PLACEHOLDER_NOTES = "Notes (optional)"
    }

    object Confirmation {
        const val TITLE = "Booking confirmed"
        const val SUBTITLE = "Your session is booked. We’ve sent the details to your email."
        const val BOOKING_ID_LABEL = "Booking ID"
        const val DETAIL_SERVICE = "Service"
        const val DETAIL_DATE_TIME = "Date & time"
        const val DETAIL_COACH = "Coach"
        const val ADD_TO_CALENDAR = "Add to Calendar"
        const val BACK_TO_HOME = "Back to Home"
    }

    object Auth {
        const val WELCOME_BACK = "Welcome back"
        const val LOGIN_SUBTITLE = "Log in to manage your bookings."
        const val LABEL_EMAIL = "Email"
        const val LABEL_PASSWORD = "Password"
        const val FORGOT_PASSWORD = "Forgot?"
        const val KEEP_LOGGED_IN = "Keep me logged in"
        const val LOGIN_BUTTON = "Log in"
        const val LOGIN_BUTTON_CONTENT_DESC = "Log in"
        const val CONTINUE_GOOGLE = "Continue with Google"
        const val CONTINUE_APPLE = "Continue with Apple"
        const val NO_ACCOUNT_PREFIX = "Don’t have an account? "
        const val SIGN_UP = "Sign up"
        const val PLACEHOLDER_EMAIL = "jordan@email.com"
        const val PLACEHOLDER_PASSWORD = "••••••••"
        const val PASSWORD_HIDE = "Hide password"
        const val PASSWORD_SHOW = "Show password"
        const val EMAIL_INPUT_CONTENT_DESC = "Email input"
        const val PASSWORD_INPUT_CONTENT_DESC = "Password input"
        const val ERROR_BANNER = "Please fix the highlighted fields before continuing."
        const val WELCOME_TOAST = "Welcome back!"
    }

    object Profile {
        const val TITLE = "Profile"
        const val MY_BOOKINGS = "My bookings"
        const val PAYMENT_METHODS = "Payment methods"
        const val NOTIFICATIONS = "Notifications"
        const val LOG_OUT = "Log out"
    }

    object Contact {
        const val TITLE = "Contact"
        const val SUBTITLE = "We reply within a day."
        const val INFO_EMAIL_LABEL = "Email"
        const val INFO_EMAIL_VALUE = "hello@bookease.app"
        const val INFO_PHONE_LABEL = "Phone"
        const val INFO_PHONE_VALUE = "+1 (415) 555-0100"
        const val INFO_STUDIO_LABEL = "Studio"
        const val INFO_STUDIO_VALUE = "540 Market St, SF"
        const val SEND_MESSAGE_TITLE = "Send a message"
        const val PLACEHOLDER_NAME = "Jordan Hayes"
        const val PLACEHOLDER_EMAIL = "jordan@email.com"
        const val PLACEHOLDER_MESSAGE = "Tell us what you need…"
        const val FIELD_NAME_LABEL = "Name"
        const val FIELD_EMAIL_LABEL = "Email"
        const val FIELD_MESSAGE_LABEL = "Message"
        const val NAME_INPUT_CONTENT_DESC = "Name input"
        const val EMAIL_INPUT_CONTENT_DESC = "Email input"
        const val MESSAGE_INPUT_CONTENT_DESC = "Message input"
        const val SEND_CONTENT_DESC = "Send message"
        const val SUCCESS_TOAST = "Message sent! We’ll reply within a day."
    }

    object Pricing {
        const val TITLE = "Pricing"
        const val SUBTITLE = "Start free or save with a plan."
        const val BILLING_MONTHLY = "Monthly"
        const val BILLING_YEARLY = "Yearly  (save 20%)"
        const val PLAN_STARTER = "Starter"
        const val PLAN_PRO = "Pro"
        const val PLAN_ELITE = "Elite"
        const val MOST_POPULAR = "MOST POPULAR"
        const val STARTER_PRICE = "₹0"
        const val STARTER_BILLING_NOTE = "Pay per session"
        const val PRO_BILLING_NOTE = "8 sessions / month"
        const val ELITE_BILLING_NOTE = "Unlimited sessions"
    }

    object HowItWorks {
        const val EYEBROW = "HOW IT WORKS"
        const val TITLE = "Booked in three steps"
        const val SUBTITLE = "From browsing to booking, we’ve made it effortless. No phone calls, no waiting — just tap and you’re on the schedule."
        const val STEP1_TITLE = "Choose a service"
        const val STEP1_DESC = "Browse our curated catalog of fitness, wellness, and nutrition sessions. Filter by category, coach, or price to find the perfect match for your goals."
        const val STEP2_TITLE = "Pick a time slot"
        const val STEP2_DESC = "Select a date and time that works for you from real-time availability. Your slot is held for 10 minutes while you complete the booking."
        const val STEP3_TITLE = "Confirm & go"
        const val STEP3_DESC = "Review your booking summary, pay securely in-app, and receive an instant confirmation. Calendar sync and reminders handle the rest."
        const val GUARANTEE_INSTANT_CONFIRMATION = "Instant confirmation"
        const val GUARANTEE_FREE_RESCHEDULING = "Free rescheduling"
        const val GUARANTEE_SECURE_PAYMENTS = "Secure payments"
        const val GET_STARTED_CONTENT_DESC = "Get started"
    }

    object Explore {
        const val TITLE = "Services"
        const val FILTER_CONTENT_DESC = "Filter"
        const val SEARCH_PLACEHOLDER = "Search"
        const val EMPTY_TITLE = "No services found"
        const val EMPTY_MESSAGE = "Try a different search or filter"
    }

    object Detail {
        const val BACK_CONTENT_DESC = "Back"
        const val WHATS_INCLUDED = "What’s included"
        const val AVAILABLE_TODAY = "Available today"
        const val TOTAL_LABEL = "Total"
    }

    object NotFound {
        const val TITLE = "Page not found"
        const val MESSAGE = "This page may have moved or the link is broken.\nLet’s get you back."
        const val BACK_CONTENT_DESC = "Back to home"
        const val BROWSE_CONTENT_DESC = "Browse services"
    }

    object Errors {
        const val DEFAULT_TITLE = "Something went wrong"
        const val DEFAULT_MESSAGE = "We hit a snag. Please try again in a moment."
        const val TRY_AGAIN = "Try again"
        const val CONTACT_SUPPORT = "Contact support"
        const val ERROR_CODE_PREFIX = "Error code: "
        const val LOGIN_INVALID_EMAIL = "Enter a valid email address"
        const val LOGIN_PASSWORD_REQUIRED = "Password is required"
        const val CONTACT_NAME_REQUIRED = "Name is required"
        const val CONTACT_INVALID_EMAIL = "Enter a valid email address"
        const val CONTACT_MESSAGE_TOO_SHORT = "Message must be at least 10 characters"
    }

    object Toast {
        const val TITLE_SUCCESS = "Success"
        const val TITLE_ERROR = "Error"
        const val TITLE_INFO = "Info"
        const val TITLE_WARNING = "Warning"
        const val DISMISS_CONTENT_DESC = "Dismiss"
    }
}
