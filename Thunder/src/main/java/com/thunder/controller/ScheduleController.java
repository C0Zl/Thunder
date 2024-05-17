package com.thunder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thunder.model.dto.Schedule;
import com.thunder.model.dto.ScheduleSearchCondtion;
import com.thunder.model.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/thunder")
@Tag(name = "ScheduleController", description = "번개 CRUD")
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@Autowired
	public ScheduleController(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}
	
	// 번개 검색 및 조회
    @Operation(summary = "번개 조회 및 검색")
    @PostMapping("/search")
    public ResponseEntity<?> searchSchedule(@RequestBody ScheduleSearchCondtion condition){
        
        List<Schedule> list = scheduleService.getScheduleList(condition.getDateTime(), condition.getCategoty(), condition.getAddressName());
        
        // 검색 결과가 없을 때 204 응답
        if(list.size()==0) {
            return ResponseEntity.noContent().build();
        }
        
        // 정상 응답 200
        return ResponseEntity.ok(list);
        
    }
    
    
	// 상세 번개 조회
	@Operation(summary = "상세 번개 조회")
	@GetMapping("/{scheduleId}")
	public ResponseEntity<?> getScheduleDetail(@PathVariable("scheduleId") int scheduleId){
		
		Schedule schedule = scheduleService.getOneSchedule(scheduleId);
		// 조회된 번개가 없으면 not found
		if(schedule == null) {
			return ResponseEntity.notFound().build();
		}
		// 조회된 번개가 있으면 번개 정보와 200 응답
		return ResponseEntity.ok(schedule);
	}
	
	
	
}
