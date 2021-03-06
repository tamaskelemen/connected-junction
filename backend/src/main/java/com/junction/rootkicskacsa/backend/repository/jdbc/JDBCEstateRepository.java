package com.junction.rootkicskacsa.backend.repository.jdbc;

import com.junction.rootkicskacsa.backend.model.Estate;
import com.junction.rootkicskacsa.backend.model.EstatePhoto;
import com.junction.rootkicskacsa.backend.model.EstateSimplified;
import com.junction.rootkicskacsa.backend.repository.EstateRepository;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.EstatePhotoRowMapper;
import com.junction.rootkicskacsa.backend.repository.jdbc.mapper.EstateRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JDBCEstateRepository implements EstateRepository {

    private final JdbcTemplate jdbc;

    private final EstateRowMapper estateRowMapper;
    private final EstatePhotoRowMapper estatePhotoRowMapper;

    @Override
    public List<Estate> findAll() {
        return jdbc.query("SELECT * FROM estates;", estateRowMapper);
    }

    @Override
    public List<EstateSimplified> findAllRelevant() {

        String sql = "select id, " +
          "data -> 'Keys' ->> '@objectId' as object_id," +
          "data -> 'Housing' ->> 'Coating' as housing_coating, " +
          "data -> 'Housing' ->> 'NumberOfRooms' as housing_number_of_rooms, " +
          "data -> 'Housing' ->> 'TotalArea' as housing_total_area, " +
          "data -> 'Building' ->> 'EnergyClass' as building_energy_class, " +
          "data -> 'Category' as category, " +
          "data -> 'ObjectType' as object_type, " +
          "data -> 'SalesPrice' as sales_price, " +
          "data -> 'Renovations' as renovations, " +
          "data -> 'RentPerMonth' as rent_per_month, " +
          "data -> 'BuildingPlanSituation' as building_plan_situation, " +
          "data -> 'UnencumberedSalesPrice' as unencumbered_sales_price, " +
          "data -> 'Location' ->> 'Coordinates' as location_coordinates, " +
          "data -> 'Building' ->> 'YearOfFirstUse' as building_year_of_first_use " +
        "from estates;";

        return jdbc.query(sql, (row, i) -> EstateSimplified.builder()
          .id(row.getLong("id"))
          .objectId(row.getLong("object_id"))
          .housingCoating(row.getString("housing_coating"))
          .housingNumberOfRooms(row.getString("housing_number_of_rooms"))
          .housingTotalArea(row.getString("housing_total_area"))
          .buildingEnergyClass(row.getString("building_energy_class"))
          .category(row.getString("category"))
          .objectType(row.getString("object_type"))
          .salesPrice(row.getString("sales_price"))
          .renovations(row.getString("renovations"))
          .rentPerMonth(row.getString("rent_per_month"))
          .buildingPlanSituation(row.getString("building_plan_situation"))
          .unencumberedSalesPrice(row.getString("unencumbered_sales_price"))
          .coordinates(row.getString("location_coordinates"))
          .buildingYearOfFirstUse(row.getInt("building_year_of_first_use"))
          .build());
    }

    @Override
    public Optional<EstatePhoto> getEstatePhoto(Long objectId) {
        String sql = "select text(data->'Links'->'Pictures'->'Picture') as pictures from estates where data->'Keys'->>'@objectId' = text(?);";
        EstatePhoto estatePhoto = jdbc.queryForObject(sql, new Object[]{objectId}, estatePhotoRowMapper);
        return Optional.ofNullable(estatePhoto);
    }
}
