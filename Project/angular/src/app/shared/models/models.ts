export type BookingStatus = 'pending' | 'confirmed' | 'completed' | 'cancelled';
export type UserRole = 'customer' | 'staff' | 'owner';

export interface Category {
  id: string;
  name: string;
  slug: string;
  icon?: string;
  sortOrder: number;
}

export interface Service {
  id: string;
  name: string;
  description: string;
  priceCents: number;
  durationMin: number;
  categoryId: string;
  categoryName?: string;
  imageUrl?: string;
  rating?: number;
  coachName?: string;
  coachId?: string;
  active: boolean;
  createdAt: string;
  included?: string[];
}

export interface TimeSlot {
  id: string;
  startsAt: string;
  isBooked: boolean;
  coachId: string;
  coachName: string;
}

export interface Booking {
  id: string;
  ref: string;
  serviceId: string;
  serviceName?: string;
  serviceImageUrl?: string;
  coachId: string;
  coachName?: string;
  userId: string;
  startsAt: string;
  status: BookingStatus;
  notes?: string;
  priceCents: number;
  createdAt: string;
  fullName?: string;
  phone?: string;
}

export interface User {
  id: string;
  fullName: string;
  email: string;
  phone?: string;
  role: UserRole;
  avatarUrl?: string;
  createdAt: string;
}

export interface AdminStats {
  totalBookings: number;
  totalRevenueCents: number;
  totalCustomers: number;
  activeServices: number;
}

export interface WeeklyAnalytic {
  day: string;
  bookings: number;
  revenueCents: number;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
}

// ---- Mock Data ----

export const MOCK_CATEGORIES: Category[] = [
  { id: 'cat-1', name: 'Strength',  slug: 'strength',  icon: '🏋️', sortOrder: 1 },
  { id: 'cat-2', name: 'HIIT',      slug: 'hiit',      icon: '⚡', sortOrder: 2 },
  { id: 'cat-3', name: 'Mobility',  slug: 'mobility',  icon: '🧘', sortOrder: 3 },
  { id: 'cat-4', name: 'Nutrition', slug: 'nutrition', icon: '🥗', sortOrder: 4 },
];

export const MOCK_SERVICES: Service[] = [
  {
    id: 'svc-1', name: 'Power Strength Session',
    description: 'A high-intensity strength training session focused on compound movements. Build muscle, increase metabolism, and improve athletic performance.',
    priceCents: 6500, durationMin: 60, categoryId: 'cat-1', categoryName: 'Strength',
    rating: 4.9, coachName: 'Marcus Reid', coachId: 'coach-1', active: true,
    createdAt: '2024-01-01T00:00:00Z',
    included: ['Personalised warm-up', 'Compound lift coaching', 'Progress tracking', 'Cool-down & mobility'],
  },
  {
    id: 'svc-2', name: 'HIIT Cardio Blast',
    description: 'Burn calories and boost cardiovascular fitness with this high-intensity interval training class. Scalable for all levels.',
    priceCents: 4500, durationMin: 45, categoryId: 'cat-2', categoryName: 'HIIT',
    rating: 4.8, coachName: 'Sofia Cheng', coachId: 'coach-2', active: true,
    createdAt: '2024-01-02T00:00:00Z',
    included: ['Dynamic warm-up', 'Interval circuits', 'Heart rate monitoring', 'Recovery stretching'],
  },
  {
    id: 'svc-3', name: 'Mobility & Flexibility',
    description: 'Improve your range of motion, reduce injury risk and recover faster. Targets joint health, muscle flexibility and movement quality.',
    priceCents: 5500, durationMin: 50, categoryId: 'cat-3', categoryName: 'Mobility',
    rating: 4.7, coachName: 'Priya Nair', coachId: 'coach-3', active: true,
    createdAt: '2024-01-03T00:00:00Z',
    included: ['Joint mobility drills', 'Fascial release', 'Yoga-inspired flows', 'Breathing exercises'],
  },
  {
    id: 'svc-4', name: 'Nutrition Coaching',
    description: '1-on-1 nutrition consultation. Personalised meal plan aligned with your fitness goals, dietary needs and lifestyle.',
    priceCents: 9500, durationMin: 60, categoryId: 'cat-4', categoryName: 'Nutrition',
    rating: 5.0, coachName: 'Dr. Lena Torres', coachId: 'coach-4', active: true,
    createdAt: '2024-01-04T00:00:00Z',
    included: ['Full dietary assessment', 'Custom meal plan', 'Supplement guidance', 'Weekly check-ins'],
  },
  {
    id: 'svc-5', name: 'Olympic Lifting Intro',
    description: 'Learn snatch and clean & jerk fundamentals from a certified coach. Safe technical instruction for beginners and intermediate athletes.',
    priceCents: 7500, durationMin: 75, categoryId: 'cat-1', categoryName: 'Strength',
    rating: 4.9, coachName: 'Marcus Reid', coachId: 'coach-1', active: true,
    createdAt: '2024-01-05T00:00:00Z',
    included: ['Technique video review', 'Barbell skill drills', 'Safety briefing', 'Programming advice'],
  },
  {
    id: 'svc-6', name: 'Recovery & Rehab',
    description: 'Targeted recovery using soft tissue work, corrective exercises and breath work. Ideal post-competition or after intense training blocks.',
    priceCents: 6000, durationMin: 60, categoryId: 'cat-3', categoryName: 'Mobility',
    rating: 4.6, coachName: 'Priya Nair', coachId: 'coach-3', active: true,
    createdAt: '2024-01-06T00:00:00Z',
    included: ['Postural assessment', 'Foam rolling & massage', 'Corrective movements', 'Sleep & recovery tips'],
  },
];

export const MOCK_BOOKINGS: Booking[] = [
  {
    id: 'bk-1', ref: 'BK-20416', serviceId: 'svc-1', serviceName: 'Power Strength Session',
    coachId: 'coach-1', coachName: 'Marcus Reid', userId: 'user-1',
    startsAt: new Date(Date.now() + 86400000).toISOString(),
    status: 'confirmed', priceCents: 6500, createdAt: new Date().toISOString(),
    fullName: 'Alex Johnson', phone: '+1 555 012-3456',
  },
  {
    id: 'bk-2', ref: 'BK-20389', serviceId: 'svc-2', serviceName: 'HIIT Cardio Blast',
    coachId: 'coach-2', coachName: 'Sofia Cheng', userId: 'user-1',
    startsAt: new Date(Date.now() + 4 * 86400000).toISOString(),
    status: 'pending', priceCents: 4500, createdAt: new Date().toISOString(),
    fullName: 'Alex Johnson', phone: '+1 555 012-3456',
  },
  {
    id: 'bk-3', ref: 'BK-20310', serviceId: 'svc-3', serviceName: 'Mobility & Flexibility',
    coachId: 'coach-3', coachName: 'Priya Nair', userId: 'user-2',
    startsAt: new Date(Date.now() - 7 * 86400000).toISOString(),
    status: 'completed', priceCents: 5500, createdAt: new Date().toISOString(),
    fullName: 'Jordan Hayes', phone: '+1 555 999-0000',
  },
  {
    id: 'bk-4', ref: 'BK-20290', serviceId: 'svc-4', serviceName: 'Nutrition Coaching',
    coachId: 'coach-4', coachName: 'Dr. Lena Torres', userId: 'user-3',
    startsAt: new Date(Date.now() - 14 * 86400000).toISOString(),
    status: 'completed', priceCents: 9500, createdAt: new Date().toISOString(),
    fullName: 'Sam Rivera', phone: '+1 555 777-1234',
  },
  {
    id: 'bk-5', ref: 'BK-20201', serviceId: 'svc-5', serviceName: 'Olympic Lifting Intro',
    coachId: 'coach-1', coachName: 'Marcus Reid', userId: 'user-1',
    startsAt: new Date(Date.now() - 21 * 86400000).toISOString(),
    status: 'cancelled', priceCents: 7500, createdAt: new Date().toISOString(),
    fullName: 'Alex Johnson', phone: '+1 555 012-3456',
  },
];

export const MOCK_TIME_SLOTS: TimeSlot[] = [
  { id: 'ts-1', startsAt: '', isBooked: false, coachId: 'coach-1', coachName: 'Marcus Reid' },
  { id: 'ts-2', startsAt: '', isBooked: false, coachId: 'coach-1', coachName: 'Marcus Reid' },
  { id: 'ts-3', startsAt: '', isBooked: true,  coachId: 'coach-1', coachName: 'Marcus Reid' },
  { id: 'ts-4', startsAt: '', isBooked: false, coachId: 'coach-1', coachName: 'Marcus Reid' },
  { id: 'ts-5', startsAt: '', isBooked: false, coachId: 'coach-1', coachName: 'Marcus Reid' },
  { id: 'ts-6', startsAt: '', isBooked: false, coachId: 'coach-1', coachName: 'Marcus Reid' },
].map((s, i) => ({
  ...s,
  startsAt: new Date(new Date().setHours(9 + i * 1.5, 0, 0, 0)).toISOString(),
}));

export const MOCK_WEEKLY_ANALYTICS: WeeklyAnalytic[] = Array.from({ length: 7 }, (_, i) => ({
  day: new Date(Date.now() - (6 - i) * 86400000).toLocaleDateString('en-US', { weekday: 'short' }),
  bookings: Math.floor(Math.random() * 10) + 2,
  revenueCents: (Math.floor(Math.random() * 10) + 2) * 6000,
}));
