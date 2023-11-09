package tingeso_mingeso.backendestudiantesservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tingeso_mingeso.backendestudiantesservice.entity.EstudianteEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity,Long> {

    public EstudianteEntity findByRut(String rut);

    //dos maneras diferentes de hacer una consulta a nuestra BD,
    // en este caso para buscar por su nombre
    @Query("select e from EstudianteEntity e where e.nombre= :nombre")
    EstudianteEntity findByNameCustomQuery(@Param("nombre")String nombre);

    @Query(value = "select * from estudiantes as e where e.nombre= :nombre",
            nativeQuery = true)
    EstudianteEntity findByNameNativeQuery(@Param("nombre")String nombre);

    @Query(value ="select * from estudiantes",
            nativeQuery = true)
    ArrayList<EstudianteEntity> findAllStudents();

}
