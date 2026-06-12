import Foundation
import Observation

@Observable
final class ServiceListViewModel {
    var allServices: [Service] = MockData.services
    var searchText: String = ""
    var selectedCategory: String = "All"

    let filterCategories: [String] = ["All"] + MockData.categories.map(\.name)

    var filteredServices: [Service] {
        allServices.filter { service in
            let matchesSearch = searchText.isEmpty ||
                service.name.localizedCaseInsensitiveContains(searchText) ||
                service.coachName.localizedCaseInsensitiveContains(searchText)

            let matchesCategory: Bool
            if selectedCategory == "All" {
                matchesCategory = true
            } else {
                let cat = MockData.categories.first { $0.name == selectedCategory }
                matchesCategory = cat.map { service.categoryId == $0.id } ?? true
            }

            return matchesSearch && matchesCategory
        }
    }
}
