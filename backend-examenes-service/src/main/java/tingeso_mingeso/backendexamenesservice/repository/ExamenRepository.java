package tingeso_mingeso.backendexamenesservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tingeso_mingeso.backendexamenesservice.entity.ExamenEntity;

@Repository
public interface ExamenRepository extends JpaRepository<ExamenEntity,Long> {

    //public ArrayList<ExamenEntity> findByRut_estudiante(String rut);

    //dos maneras diferentes de hacer una consulta a nuestra BD,
    // en este caso para buscar por su nombre
    @Query("select e from ExamenEntity e where e.id= :id")
    ExamenEntity findByIdCustomQuery(@Param("id")int id);

    @Query(value = "select * from examenes as e where e.id= :id",
            nativeQuery = true)
    ExamenEntity findByIdNativeQuery(@Param("id")int id);

    @Query(value = "select count(*) from examenes as e where e.rut_estudiante=:rut_estudiante",
            nativeQuery = true)
    int obetenerNumExamenes(@Param("rut_estudiante")String rut_estudiante);

    @Query(value = "select avg(e.puntaje) from examenes as e where e.rut_estudiante=:rut_estudiante",
            nativeQuery = true)
    double obetenerpromedioExamenes(@Param("rut_estudiante")String rut_estudiante);

}
